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
package org.skfiy.maven.plugin.fastconfig.model;

/**
 * &lt;replacement&gt;节点对象.
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
public class Replacement {

  private String expression;
  private String value;

  public Replacement() {
  }

  /**
   *
   * @param expression 替换表达式
   * @param value 目标值
   */
  public Replacement(String expression, String value) {
    this.expression = expression;
    this.value = value;
  }

  public String getExpression() {
    return expression;
  }

  public void setExpression(String expression) {
    this.expression = expression;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
