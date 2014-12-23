package org.skfiy.maven.plugin.fastconfig;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.skfiy.maven.plugin.fastconfig.model.ConfigFile;
import org.skfiy.maven.plugin.fastconfig.model.Replacement;
import org.skfiy.maven.plugin.fastconfig.model.Fastconfig;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.PluginParameterExpressionEvaluator;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
@Mojo(name = "configure", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class FastconfigMojo extends AbstractMojo {

    @Parameter(required = true)
    private File config;
    @Parameter(required = true, readonly = true, defaultValue = "${project.build.sourceEncoding}")
    private String encoding;
    @Parameter(readonly = true, defaultValue = "${session}")
    private MavenSession session;
    @Parameter(readonly = true, defaultValue = "${mojo}")
    private MojoExecution execution;
    @Parameter(readonly = true, defaultValue = "${project.build.outputDirectory}")
    private File outputDirectory;

    @Override
    public void execute() throws MojoExecutionException {
        if (config == null || !config.exists()) {
            getLog().warn("no fast-config file is provided, skipping running.");
        }

        Fastconfig fastconfig;
        try {
            fastconfig = buildFastconfig();
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

        BasicProcessor processor = new BasicProcessor(encoding);
        for (ConfigFile cf : fastconfig.getConfigFiles()) {
            if (processor.isSupported(cf.getFile())) {
                processor.process(cf);
            }
        }
    }

    private Fastconfig buildFastconfig() throws Exception {
        PluginParameterExpressionEvaluator pel = new PluginParameterExpressionEvaluator(session, execution);
        Fastconfig fastconfig = new Fastconfig();

        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(config);
        Element root = doc.getRootElement();

        // config-file
        for (Element cf : root.getChildren()) {
            String path = String.valueOf(pel.evaluate(cf.getAttributeValue("path")));
            File file = new File(path);
            if (!file.isAbsolute()) {
                file = new File(outputDirectory, path);
            }

            ConfigFile.Mode mode;
            if (StringUtils.isNotEmpty(cf.getAttributeValue("mode"))) {
                mode = ConfigFile.Mode.valueOf(cf.getAttributeValue("mode"));
            } else {
                mode = toMode(path.substring(path.lastIndexOf(".") + 1));
            }

            if (mode == null) {
                throw new FastconfigException("Not found file[" + path + "] replace mode");
            }

            ConfigFile configFile = new ConfigFile(file, mode);
            // replacement
            for (Element rt : cf.getChildren()) {
                String expression = rt.getAttributeValue("expression");
                String value = String.valueOf(pel.evaluate(rt.getTextTrim()));
                configFile.addReplacement(new Replacement(expression, value));
            }
            fastconfig.addConfigFile(configFile);
        }
        return fastconfig;
    }

    private ConfigFile.Mode toMode(String suffix) {
        if ("xml".equals(suffix)) {
            return ConfigFile.Mode.xpath;
        } else if ("json".equals(suffix)) {
            return ConfigFile.Mode.jsonpath;
        } else if ("properties".equals(suffix)) {
            return ConfigFile.Mode.property;
        }
        return null;
    }

}
