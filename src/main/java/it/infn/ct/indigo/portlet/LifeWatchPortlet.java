package it.infn.ct.indigo.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.portlet.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.PrintWriter;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=INFN",
		"com.liferay.portlet.header-portlet-javascript=/js/fg-api.js",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=LifeWatch Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
//@Controller("galaxyPortlet")
//@RequestMapping(value = "VIEW")
public class LifeWatchPortlet extends MVCPortlet {
    final private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss,SSS");

    private String logEvent(String text) {
        String log = sdf.format(new Date()) + " " + text;
        System.out.println(log);
        return log;
    }

    public void randomNumber(ActionRequest request, ActionResponse renderResponse){
        logEvent("# randomNumber()");
        Random generator = new Random();
        double number = generator.nextDouble();
        request.setAttribute("randomNumber", String.valueOf(number));
    }

    @Override
    public void serveResource(ResourceRequest resourceRequest,
            ResourceResponse resourceResponse) throws IOException,
    PortletException {
        try {
            String ans = ParamUtil.getString(resourceRequest, "json");
            JsonObject jsonObject = new Gson().fromJson(ans, JsonObject.class);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String out = gson.toJson(jsonObject);

            createParamFile(out);
        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
        super.serveResource(resourceRequest, resourceResponse);
    }
    
    public void createParamFile(String json) {
        File file = new File("/home/futuregateway/FutureGateway/fgAPIServer/apps/toscaOnecloudTest/parameters.json");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            printWriter.print(json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }

    private String readFile(String path) {
        String content = null;
        try {
            if(new File(path).isFile()) {
                content = new String(Files.readAllBytes(Paths.get(path)));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
    private String readJsonFile(String pathToFile) {
        String json = readFile(pathToFile);
        if(json == null) {
            //json = "{\n  \"parameters\": [\n    {\n      \"display\": \"Input Onedata Token\",\n      \"name\": \"input_onedata_token\",\n      \"type\": \"text\",\n      \"value\": \"token\"\n    },\n    {\n      \"display\": \"Output Onedata Token\",\n      \"name\": \"output_onedata_token\",\n      \"type\": \"text\",\n      \"value\": \"token\"\n    },\n    {\n      \"display\": \"Input Onedata Space\",\n      \"name\": \"input_onedata_space\",\n      \"type\": \"text\",\n      \"value\": \"input_space\"\n    },\n    {\n      \"display\": \"Output Onedata Space\",\n      \"name\": \"output_onedata_space\",\n      \"type\": \"text\",\n      \"value\": \"output_space\"\n    },\n    {\n      \"display\": \"Input Onedata Providers\",\n      \"name\": \"input_onedata_providers\",\n      \"type\": \"text\",\n      \"value\": \"cdmi-indigo.recas.ba.infn.it\"\n    },\n    {\n      \"display\": \"Output Onedata Providers\",\n      \"name\": \"output_onedata_providers\",\n      \"type\": \"text\",\n      \"value\": \"cdmi-indigo.recas.ba.infn.it\"\n    },\n    {\n      \"display\": \"Input Path\",\n      \"name\": \"input_path\",\n      \"type\": \"text\",\n      \"value\": \"input\"\n    },\n    {\n      \"display\": \"Output Path\",\n      \"name\": \"output_path\",\n      \"type\": \"text\",\n      \"value\": \"output\"\n    },\n    {\n      \"display\": \"Output Filenames\",\n      \"name\": \"output_filenames\",\n      \"type\": \"text\",\n      \"value\": \"sample.txt\"\n    }\n  ]\n}";
            json = "{\n  \"version_of_portlet_description\": 0.2,  \n  \"tabs\": [\"Input Parameters\", \"Output Parameters\", \"D3D Input\"],\n  \"parameters\": [\n    {\n      \"display\": \"Input Onedata Token\",\n      \"name\": \"input_onedata_token\",\n      \"type\": \"text\",\n      \"value\": \"token\",\n      \"tab\": 0\n    },\n    {\n      \"display\": \"Output Onedata Token\",\n      \"name\": \"output_onedata_token\",\n      \"type\": \"text\",\n      \"value\": \"token\",\n      \"tab\": 1\n    },\n    {\n      \"display\": \"Input Onedata Space\",\n      \"name\": \"input_onedata_space\",\n      \"type\": \"text\",\n      \"value\": \"AlgaeBloom\",\n      \"tab\": 0\n    },\n    {\n      \"display\": \"Output Onedata Space\",\n      \"name\": \"output_onedata_space\",\n      \"type\": \"text\",\n      \"value\": \"AlgaeBloom\",\n      \"tab\": 1\n    },\n    {\n      \"display\": \"Input Onedata Providers\",\n      \"name\": \"input_onedata_providers\",\n      \"type\": \"text\",\n      \"value\": \"cdmi-indigo.recas.ba.infn.it\",\n      \"tab\": 0\n    },\n    {\n      \"display\": \"Output Onedata Providers\",\n      \"name\": \"output_onedata_providers\",\n      \"type\": \"text\",\n      \"value\": \"cdmi-indigo.recas.ba.infn.it\",\n      \"tab\": 1\n    },\n    {\n      \"display\": \"Input Path\",\n      \"name\": \"input_path\",\n      \"type\": \"text\",\n      \"value\": \"input\",\n      \"tab\": 0\n    },\n    {\n      \"display\": \"Output Path\",\n      \"name\": \"output_path\",\n      \"type\": \"text\",\n      \"value\": \"output\",\n      \"tab\": 1\n    },\n    {\n      \"display\": \"Input Config File\",\n      \"name\": \"input_config_file\",\n      \"type\": \"text\",\n      \"value\": \"com-tut_fti_waq.inp\",\n      \"tab\": 0\n    },\n    {\n      \"display\": \"Output Filenames\",\n      \"name\": \"output_filenames\",\n      \"type\": \"text\",\n      \"value\": \"output.tgz\",\n      \"tab\": 1\n    },\n    {\n      \"display\": \"D3D Param\",\n      \"name\": \"d3d_param\",\n      \"type\": \"text\",\n      \"value\": \"\",\n      \"tab\": 2\n    },\n    {\n      \"display\": \"D3D Value\",\n      \"name\": \"d3d_value\",\n      \"type\": \"text\",\n      \"value\": \"\",\n      \"tab\": 2\n    }\n  ]\n}";
        }
        return json;
    }

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        renderRequest.setAttribute("json-array", readJsonFile("lifewatch-template.json"));
        //renderRequest.setAttribute("token", readFile("/tmp/token"));
        super.doView(renderRequest, renderResponse);
    }
}
