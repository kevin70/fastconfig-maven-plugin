/*
 * Copyright 2014 Kevin Zou <kevinz@skfiy.org>.
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
package org.skfiy.maven.plugin.fastconfig;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.skfiy.maven.plugin.fastconfig.internal.JSONPathConfigurator;
import org.skfiy.maven.plugin.fastconfig.internal.PropertyConfigurator;
import org.skfiy.maven.plugin.fastconfig.internal.RegexConfigurator;
import org.skfiy.maven.plugin.fastconfig.internal.XPathConfigurator;
import org.skfiy.maven.plugin.fastconfig.model.ConfigFile;

/**
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
public abstract class Processor {

    private static final Map<ConfigFile.Mode, Configurator> configurators = new HashMap<>();

    static {
        configurators.put(ConfigFile.Mode.property, new PropertyConfigurator());
        configurators.put(ConfigFile.Mode.regex, new RegexConfigurator());
        configurators.put(ConfigFile.Mode.xpath, new XPathConfigurator());
        configurators.put(ConfigFile.Mode.jsonpath, new JSONPathConfigurator());
    }

    protected final Charset charset;

    public Processor(String encoding) {
        this.charset = Charset.forName(encoding);
    }

    /**
     *
     * @param configFile
     */
    public abstract void process(ConfigFile configFile);

    /**
     *
     * @param file
     * @return
     */
    public abstract boolean isSupported(File file);

    /**
     *
     * @param mode
     * @return
     */
    protected final Configurator getConfigurator(ConfigFile.Mode mode) {
        return configurators.get(mode);
    }

}
