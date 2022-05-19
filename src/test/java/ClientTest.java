import org.harbor.client.HarborClientBuilder;
import org.harbor.client.HarborClientV2;
import org.harbor.client.HarborResponse;
import org.harbor.client.model.Artifact;
import org.harbor.client.model.GeneralInfo;
import org.harbor.client.model.NativeReportSummary;
import org.harbor.client.model.OverallHealthStatus;
import org.harbor.client.model.Project;
import org.harbor.client.model.ProjectMetadata;
import org.harbor.client.model.ProjectReq;
import org.harbor.client.model.Repository;
import org.harbor.client.model.ScannerRegistration;
import org.harbor.client.model.Search;
import org.harbor.client.model.SystemInfo;
import org.harbor.client.model.Tag;
import org.harbor.client.model.User;
import org.harbor.client.op.Tags;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lr
 * @date 2021/2/23
 */
public class ClientTest {

    HarborClientV2 clientV1 = new HarborClientBuilder()
            .setUrl("http://192.168.1.72:30000/")
            .setUsername("admin")
            .setPassword("Harbor123456")
            .setConnectionTimeout(30, TimeUnit.SECONDS)
            .buildV1();

    // --- project
    // get
    @Test
    public void get() {
        Project test = clientV1.projects().withExactName("test").get();
    }

    // delete
    @Test
    public void delete() {
        HarborResponse response = clientV1.projects().withExactName("test").delete();
    }

    // create
    @Test
    public void create() {
        ProjectReq req = new ProjectReq();
        req.setProjectName("test02231");
        req.setMetadata(new ProjectMetadata(false));
        HarborResponse harborResponse = clientV1.projects().create(req);
        System.out.println(harborResponse);
    }

    // ---- repository---

    //get
    @Test
    public void testRepoList() {
        List<Repository> boc = clientV1.projects().withExactName("boc").repositories().list(null);
        System.out.println(boc);
    }

    @Test
    public void testRepoGet() {
        Repository repository = clientV1.projects().withExactName("boc").repositories().repository("wartest").get();
        System.out.println(repository);
    }


    // --- artifact ---
    // list
    @Test
    public void testArtifactList() {
        List<Artifact> list = clientV1.projects().withExactName("boc001").repositories().repository("http")
                .artifacts().list(null);
        System.out.println(list);
        for (Map.Entry<String, NativeReportSummary> entry : list.get(0).getScanOverview().entrySet()) {
            String key = entry.getKey();
            System.out.println(key);
            System.out.println(entry.getValue());
        }
    }

    @Test
    public void testArtifactGet() {
        Artifact artifact = clientV1.projects().withExactName("boc001").repositories().repository("http")
                .artifacts().artifact("latest-20200819180420").get();
        System.out.println(artifact);

    }

    // ---- tag ---
    @Test
    public void testTagList() {
        List<Tag> tags = clientV1.projects().withExactName("boyun").repositories().repository("hello-kubernetes")
                .artifacts().artifact("1.5").tags().list(null);
        System.out.println(tags);

        Tags tag = clientV1.project("boyun").repository("hello-kubernetes").artifact("1.5").tags();
        List<Tag> list = tag.list(null);
        System.out.println(list);
    }

    @Test
    public void testTagNameDelete() {

        HarborResponse delete = clientV1.project("boyun").repository("hello-kubernetes")
                .artifact("1.5").tags().delete("test");

        System.out.println(delete);
    }

    @Test
    public void testArtifactScan() {
        HarborResponse scan = clientV1.project("boyun")
                .repository("ci11")
                .artifact("latest-20191118142130")
                .scan();
        System.out.println(scan);
    }

    @Test
    public void testSearch() {
        Search search = clientV1.search().query("http").doSearch();
        System.out.println(search);
    }

    @Test
    public void testProjectScanner() {
        ScannerRegistration boc = clientV1.project("boc001").scanner();
        System.out.println(boc);
    }

    @Test
    public void testStorage() {
        SystemInfo volumes = clientV1.systemInfo().volumes();
        System.out.println(volumes);
    }

    @Test
    public void testUser() {
        List<User> list = clientV1.user().list();
        User current = clientV1.user().get();
        System.out.println(current);
    }

    @Test
    public void testHealth() {
        OverallHealthStatus status = clientV1.health().get();
        System.out.println(status);
    }

    @Test
    public void testGeneralInfo() {
        HarborClientV2 client = new HarborClientBuilder()
                .setUrl("http://192.168.1.72:30000/")
                .setConnectionTimeout(30, TimeUnit.SECONDS)
//                .enable(ResponseConfigure.FAILED_THROW)
                .buildV1();
        GeneralInfo generalInfo = client.systemInfo().get();
        System.out.println(generalInfo);
    }

}
