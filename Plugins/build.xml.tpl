<?xml version="1.0" encoding="UTF-8"?>
<project name="LibreDémat - @plugin_name@ Plugin" default="dist" basedir=".">

  <description>Ant build file for the @plugin_name@ plugin project.</description>

  <target name="init">
    
    <!-- plugin specific properties -->
    <property file="${basedir}/build.properties" />
    
    <mkdir dir="${basedir}/${build.classes.dir}" />
    <mkdir dir="${basedir}/${build.archives.dir}" />
    <mkdir dir="${basedir}/${build.test.dir}" />

    <!-- Paths -->
    <path id="classpath.compile">
      <fileset dir="${libraries.dir}">
        <include name="**/*.jar" />
      </fileset>
    </path>

    <path id="classpath.compile.test">
      <path refid="classpath.compile" />

      <fileset dir="${model.dir}/${lib.dir}">
        <include name="**/org.springframework.test-*.jar" />
        <include name="**/junit*.jar" />
      </fileset>

      <pathelement path="${model.dir}/${build.test.dir}"/>
      <pathelement path="${basedir}/${build.classes.dir}"/>
    </path>
    
  </target>

  <!-- =================================================================== -->
  <!--                        Regular targets                              -->
  <!-- =================================================================== -->

  <target name="clean" depends="init">
    <delete dir="${basedir}/${build.dir}" />
  </target>

  <target name="compile" depends="init">
    <javac optimize="on"
           debug="on"
           deprecation="on"
           encoding="UTF-8"
           verbose="off"
           srcdir="${basedir}/${src.java.dir}"
           includes="**/*.java"
           destdir="${basedir}/${build.classes.dir}">
      <classpath refid="classpath.compile"/>
    </javac>
  </target>

  <target name="dist"
          depends="compile, compiletests"
          description="Build the @plugin_name@ plugin jar">
    <jar compress="on"
         jarfile="${basedir}/${build.archives.dir}/${application.name}-${module.name}-${application.version}.jar"
         manifest="${basedir}/${src.java.dir}/META-INF/manifest.mf">
      <fileset dir="${basedir}/${build.classes.dir}">
        <include name="**/**" />
      </fileset>
      <fileset dir="${basedir}/${build.test.dir}">
        <include name="**/**" />
      </fileset>
      <fileset dir="${basedir}/conf/spring">
        <include name="pluginContext*.xml"/>
      </fileset>
      <!--  add other directories and files here (eg, CSV mapping definition file) -->
    </jar>
  </target>

  <target name="deploy" depends="dist">
    <copy todir="${deploy.plugins.dir}" 
      file="${basedir}/${build.archives.dir}/${application.name}-${module.name}-${application.version}.jar"/>
  </target>
      
  <!-- =================================================================== -->
  <!--                     Tests                                           -->
  <!-- =================================================================== -->

  <target name="compiletests" depends="compile">
    <javac optimize="on"
           debug="on"
           deprecation="on"
           verbose="off"
           srcdir="${basedir}/${test.java.dir}"
           includes="**/*.java"
           destdir="${basedir}/${build.test.dir}">
      <classpath refid="classpath.compile.test" />
    </javac>
    <!--  add other directories and files here (eg, test data) -->
  </target>


</project>
