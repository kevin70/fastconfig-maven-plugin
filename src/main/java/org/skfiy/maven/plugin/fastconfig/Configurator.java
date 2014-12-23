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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.skfiy.maven.plugin.fastconfig.model.Replacement;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
public interface Configurator {

    /**
     *
     * @param in
     * @param charset
     * @param replacements
     * @return
     * @throws IOException
     */
    ByteArrayOutputStream execute(InputStream in, Charset charset, List<Replacement> replacements)
            throws IOException;

}
