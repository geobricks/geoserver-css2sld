package org.fao.fenix;
 
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.FileReader;
import java.util.Map;
import java.net.URL;
import org.geotools.styling.Style;
import java.io.Reader;
import java.io.StringReader;
import org.geoscript.geocss.compat.*;
import org.geoscript.geocss.*;
import org.geoscript.geocss.Converter;
import org.geotools.styling.Style;
import org.geotools.styling.SLDTransformer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class CSS2SLDServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
			Map<String,String[]> parameters = request.getParameterMap();		
			String stylename = ""; // TODO: randominc name if it's not passed

			String output = "";
			// this used if it's used as SLD_BODY the request (it modify the response from the CSS2SLD method)
			boolean isSLD_BODY = true;			
			String sldHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><sld:StyledLayerDescriptor xmlns=\"http://www.opengis.net/sld\" xmlns:sld=\"http://www.opengis.net/sld\" xmlns:ogc=\"http://www.opengis.net/ogc\" xmlns:gml=\"http://www.opengis.net/gml\" version=\"1.0.0\">";
			sldHeader +="<sld:NamedLayer>";
			String sldFooter = "</sld:NamedLayer></sld:StyledLayerDescriptor>";

			try { 
				stylename = parameters.get("stylename")[0];
			}catch(Exception e) { System.out.println("error: " + e.getMessage()); }

			try { 
				isSLD_BODY = Boolean.valueOf(parameters.get("sld_body")[0]);
			}catch(Exception e) {  }

			//String stringToBeParsed = " * {stroke: #333333, #6699FF;stroke-width: 5px, 3px;stroke-linecap: round;z-index: 0, 1;}";
			String stringToBeParsed = parameters.get("style")[0];
			StringReader reader = new StringReader(stringToBeParsed);
			Style style = CSS2SLD.convert(reader);
			style.setName(stylename);
			
			try {
				SLDTransformer transformer = new SLDTransformer();
				//transformer.setIndentation(0);
				transformer.setIndentation(0);
				// to transform directly into a file instead of a string
				//transformer.transform(style,new FileOutputStream("/home/vortex/Desktop/junk.eraseme"));
				output = transformer.transform(style);
			}catch(Exception e) { System.out.println("error: " + e.getMessage()); }


			//  modify SLD/XML result
			if ( isSLD_BODY ) {
				output = output.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
				sldHeader += "<sld:Name>" + stylename +"</sld:Name>";
				//sldHeader += "<sld:Name></sld:Name>";
				output = sldHeader + output + sldFooter;
			}

			String sldTmp = getServletContext().getInitParameter("CSS2SLDFolder");
			String url = getServletContext().getInitParameter("CSS2SLDURL");
			//System.out.println(sldTmp);
			//System.out.println(url);
			UUID id = UUID.randomUUID();
			String filename = id + ".sld";

			String path_filename = sldTmp + "/" + id + ".sld";
			String url_filename = url + "/" + id + ".sld";



			try {
				File file = new File(path_filename);
 
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
	 
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(output);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			response.setContentLength(url_filename.length());
			response.setCharacterEncoding("UTF-8");			
			response.setHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			out.println(url_filename);
			
			// close stream
			out.close();
			out.flush();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

}
