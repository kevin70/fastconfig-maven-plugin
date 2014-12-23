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
package org.skfiy.maven.plugin.fastconfig.internal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.parser.Feature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.skfiy.maven.plugin.fastconfig.Configurator;
import org.skfiy.maven.plugin.fastconfig.model.Replacement;

/**
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
public class JSONPathConfigurator implements Configurator {

    @Override
    public ByteArrayOutputStream execute(InputStream in, Charset charset,
            List<Replacement> replacements) throws IOException {
        Object json = JSON.parse(IOUtils.toByteArray(new InputStreamReader(in), charset),
                Feature.OrderedField);

        JSONPath path;
        for (Replacement repl : replacements) {
            path = JSONPath.compile(repl.getExpression());
            path.set(json, repl.getValue());
        }

        String text = JSON.toJSONString(json, true);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(text.getBytes(charset));
        return out;
    }

}
