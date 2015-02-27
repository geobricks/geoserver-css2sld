
INSTALLING THE CSS2SLD Servlet
-------------------------------

1) Install CSS modules on GeoServer lib folder

[Install CSS Module](http://docs.geoserver.org/latest/en/user/extensions/css/install.html)

2) Copy dist/org/fao/fenix/ on GeoServer WEB-INF/classes/ folder:

    $TOMCAT/webapps/geoserver/WEB-INF/classes/org/fao/fenix/

3) Copy the dist/servlet-api.jar to WEB-INF/lib/ (servlet-api is the jar used by tomcat in tomcat/lib)

    $TOMCAT/webapps/geoserver/WEB-INF/lib

4) Modify the web.xml file in geoserver/WEB-INF/web.xml mapping the Servlet

```xml

    <servlet>
      <servlet-name>CSS2SLD</servlet-name>
      <servlet-class>org.fao.fenix.CSS2SLDServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>CSS2SLD</servlet-name>
        <url-pattern>/CSS2SLD</url-pattern>
    </servlet-mapping>

   <!-- Path to be used to create SLDs -->
    <context-param>
    	<param-name>CSS2SLDFolder</param-name>
    	<param-value>$TOMCAT/webapps/geoserver/css2sld/</param-value>
    </context-param>

    <!-- Accessible URL to access the SLD -->
    <context-param>
    	<param-name>CSS2SLDURL</param-name>
    	<param-value>http://localhost:8080/geoserver/css2sld</param-value>
    </context-param>
```

USAGE
-----

The service is now accessible through a POST request at the following URL http://localhost:8080/geoserver/CSS2SLD

```json

{
    "style": "* {mark: symbol(circle);}",
    "stylename": "workspace:layername (optional and to be used in conjunction with the WMS parameter 'SLD')" 
}
```

Returns a URL $CSS2SLDURL/UUID.sld




