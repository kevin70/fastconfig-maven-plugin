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
package org.skfiy.maven.plugin.fastconfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.skfiy.maven.plugin.fastconfig.model.Replacement;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 文件配置器.
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
public interface Configurator {

  /**
   * 处理输入流并完全替换返回结果流.
   *
   * @param in 输入流
   * @param charset 编码
   * @param replacements 替换模式
   * @return 输出结果
   * @throws IOException <b>读取流/输出流</b>错误
   */
  ByteArrayOutputStream execute(InputStream in, Charset charset, List<Replacement> replacements)
          throws IOException;

}
