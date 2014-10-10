
INSTALLING THE CSS2SLD Servlet
-------------------------------


1) create under geoserver WEB-INF folder:

    tomcat_geoserver/webapps/geoserver/WEB-INF/classes/org/fao/fenix/

2) copy the CSS2SLDServlet to the folder
3) rename it to CSS2SLDServlet.java
4) copy the servlet-api.jar to (servlet-api is the jar used by tomcat in tomcat/lib)

    tomcat_geoserver/webapps/geoserver/WEB-INF/lib

5) compile the java class with the following command (launched on the org/fao/fenix/ folder)

    javac -cp '../../../../lib/*'  CSS2SLDServlet.java

6) modify the web.xml file in geoserver/WEB-INF mapping the Servlet

        <servlet-mapping>
            <servlet-name>CSS2SLD</servlet-name>
            <url-pattern>/CSS2SLD</url-pattern>
        </servlet-mapping>

        <servlet-mapping>
            <servlet-name>dispatcher</servlet-name>
            <url-pattern>/*</url-pattern>
        </servlet-mapping>



