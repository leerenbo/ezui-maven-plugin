package com.datalook.ezui.generate.plugin;

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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import com.datalook.ezui.generate.plugin.model.EzuiHolder;
import com.datalook.ezui.generate.plugin.scan.Scanner;
import com.datalook.ezui.generate.plugin.template.Templater;

@Mojo(defaultPhase = LifecyclePhase.TEST, requiresDependencyResolution = ResolutionScope.RUNTIME_PLUS_SYSTEM, name = "generate", requiresProject = true)
public class EzuiGenerateMojo extends AbstractMojo {
	
	
	private List<EzuiHolder> leh;
	private Scanner scanner;
	private Templater templater;

	/**
	 * Location of the file.
	 */
	@Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
	private File outputDirectory;

	@Parameter(property = "project")
	private MavenProject project;

	public void execute() throws MojoExecutionException {
		try {
		scanner=new Scanner(getLog());
		templater= new Templater(getLog());
		
		List<EzuiHolder> ezuiHolders=scanner.scan(project);
		getLog().info("----------检测到类"+ezuiHolders.size()+"----------");
		for (EzuiHolder ezuiHolder : ezuiHolders) {
			templater.generate(ezuiHolder);
		}
		
		File f = outputDirectory;

		if (!f.exists()) {
			f.mkdirs();
		}

		File touch = new File(f, "touch.txt");

		FileWriter w = null;
		try {
			w = new FileWriter(touch);
			w.write("touch.txt");
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + touch, e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void list(Class c){
		System.out.println("----------"+c.getName());
		Method[] ms=c.getDeclaredMethods();
		for (Method m : ms) {
			System.out.println(""+m.getReturnType());
			System.out.println("      "+m.getName()+"()");
			Class<?>[] cs=m.getParameterTypes();
			for (Class<?> class1 : cs) {
				System.out.println("            "+class1.getName());
			}
		}
		System.out.println("---属性");
		Field[] fs=c.getDeclaredFields();
		for (Field field : fs) {
			System.out.println(field);
		}
	}

}
