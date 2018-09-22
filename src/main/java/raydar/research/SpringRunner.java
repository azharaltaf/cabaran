package raydar.research;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.File;

/**
 * Created by azhar.altaf on 9/15/18.
 */
@SpringBootApplication
@PropertySource(value = {"classpath:application.yml"})
@ComponentScan()
public class SpringRunner implements CommandLineRunner {
    @Value("${app.filedir}")
    private String appFileDir;
    @Value("${app.filename}")
    private String appFileName;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringRunner.class);
        app.run(args);

    }

    /**
     * Checks if the file and directory exist. If not then created it to be used in the application.
     *
     * @param strings
     * @throws Exception
     */
    @Override
    public void run(String... strings) throws Exception {
        File directory = new File(appFileDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File file = new File(appFileDir + "/" + appFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
