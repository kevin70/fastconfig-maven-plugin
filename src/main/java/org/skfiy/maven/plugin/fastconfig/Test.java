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

import com.ximpleware.AutoPilot;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XMLModifier;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

/**
 *
 * @author Kevin Zou <kevinz@skfiy.org>
 */
public class Test {

    public static void main(String[] args) throws Exception {
//        URL url = new URL("jar:file:servlet-api-2.5.jar!/a.txt");

//        FileOutputStream fos = new FileOutputStream("servlet-api-2.5.jar");
//        JarOutputStream jos = new JarOutputStream(fos);
//        JarEntry jarEntry = new JarEntry("a.txt");
//        jarEntry.setExtra("HELLO, World".getBytes());
//        jos.putNextEntry(jarEntry);
//
////        jos.write("Hello, World.".getBytes());
//        jos.flush();
//        jos.close();
//        URL url = new URL("jar:file:servlet-api-2.5.jar!/META-INF/MANIFEST.MF");
//        File file = new File("jar:file:C:\\Users\\Administrator\\Documents\\NetBeansProjects\\fastconfig-maven-plugin\\servlet-api-2.5.jar!/META-INF/MANIFEST.MF");
//        InputStream in = new FileInputStream(file);
//        InputStream in = url.openStream();
//        Reader reader = new InputStreamReader(in);
//        BufferedReader bufReader = new BufferedReader(reader);
//        String line;
//        while ((line = bufReader.readLine()) != null) {
//            System.out.println(bufReader.readLine());
//        }
//        PropertiesConfiguration pc = new PropertiesConfiguration("hello.properties");
//        pc.setProperty("name", "koffy---");
//        
//        pc.save();
        InputStream inputStream = new FileInputStream("hello.xml");
        VTDGen vtdGen = new VTDGen();
        vtdGen.setDoc(IOUtils.toByteArray(inputStream));
        vtdGen.parse(true);

        VTDNav vtdNav = vtdGen.getNav();

        AutoPilot autoPilot = new AutoPilot(vtdNav);

        XMLModifier xmlModifier = new XMLModifier(vtdNav);

        autoPilot.selectXPath("/root/name/@[/age]");
        int i;
        while ((i = autoPilot.evalXPath()) != -1) {
            int textIndex = vtdNav.getText();
            xmlModifier.updateToken(textIndex, "koffy");
        }
        autoPilot.resetXPath();

        FileOutputStream outputStream = new FileOutputStream("hello2.xml");
        xmlModifier.output(outputStream);
        outputStream.flush();
        outputStream.close();
        File file = new File("C:\\a.txt");
        System.out.println(file.isAbsolute());
    }

}
