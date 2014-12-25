/**
 * Copyright (C) 2014-2015 The Skfiy Open Association.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.skfiy.maven.plugin.fastconfig.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.skfiy.maven.plugin.fastconfig.Configurator;
import org.skfiy.maven.plugin.fastconfig.model.Replacement;

/**
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
public class RegexConfigurator implements Configurator {

    @Override
    public ByteArrayOutputStream execute(InputStream in, Charset charset,
            List<Replacement> replacements) throws IOException {
        String text = IOUtils.toString(in, charset);

        Pattern pattern;
        Matcher matcher;
        for (Replacement repl : replacements) {
            pattern = Pattern.compile(repl.getExpression());
            matcher = pattern.matcher(text);

            while (matcher.find()) {
                text = matcher.replaceAll(repl.getValue());
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(text.getBytes(charset));
        return out;
    }

}
