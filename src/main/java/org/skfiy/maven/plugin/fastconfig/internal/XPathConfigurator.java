/**
 * Copyright (C) 2014-2015 The Skfiy Open Association.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.skfiy.maven.plugin.fastconfig.internal;

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XMLModifier;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.skfiy.maven.plugin.fastconfig.Configurator;
import org.skfiy.maven.plugin.fastconfig.model.Replacement;

/**
 * xpath配置器.
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
public class XPathConfigurator implements Configurator {

  private static final Pattern XML_ATTRIBUTE_PATTERN = Pattern.compile(".*/@[^/]+$");

  @Override
  public ByteArrayOutputStream execute(InputStream in, Charset charset,
          List<Replacement> replacements) throws IOException {
    try {
      VTDGen vtdGen = new VTDGen();
      vtdGen.setDoc(IOUtils.toByteArray(in));
      vtdGen.parse(true);

      VTDNav vtdNav = vtdGen.getNav();
      AutoPilot autoPilot = new AutoPilot(vtdNav);
      XMLModifier xmlModifier = new XMLModifier(vtdNav);

      int i;
      for (Replacement repl : replacements) {
        autoPilot.selectXPath(repl.getExpression());

        while ((i = autoPilot.evalXPath()) != -1) {
          if (isAttribute(repl.getExpression())) {
            xmlModifier.updateToken(i + 1, repl.getValue());
          } else {
            xmlModifier.updateToken(vtdNav.getText(), repl.getValue());
          }
        }
        autoPilot.resetXPath();
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      xmlModifier.output(out);
      return out;
    } catch (Exception ex) {
      // FIXME
      throw new RuntimeException(ex);
    }
  }

  private boolean isAttribute(String key) {
    return XML_ATTRIBUTE_PATTERN.matcher(key).matches();
  }
}
