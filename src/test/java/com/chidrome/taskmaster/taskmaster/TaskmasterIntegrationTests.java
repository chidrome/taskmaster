package com.chidrome.taskmaster.taskmaster;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.chidrome.taskmaster.taskmaster.models.TaskInfo;
import com.chidrome.taskmaster.taskmaster.repositories.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TaskmasterApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
public class TaskmasterIntegrationTests {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TaskRepository taskRepository;

    private static final String EXPECTED_TITLE = "Table";
    private static final String EXPECTED_DESCRIPTION = "buy computer desk";
    private static final String EXPECTED_ASSIGNEE = "paolo";
    private static final String EXPECTED_STATUS = "available";
    private static final String EXPECTED_IMAGE = "";

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(TaskInfo.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

//        dynamoDBMapper.batchDelete((List<TaskInfo>)taskRepository.findAll());
    }

    @Test
    public void readWriteTestCase() {
        TaskInfo dave = new TaskInfo(EXPECTED_TITLE, EXPECTED_DESCRIPTION, EXPECTED_ASSIGNEE, EXPECTED_STATUS, EXPECTED_IMAGE);
        taskRepository.save(dave);

        List<TaskInfo> result = (List<TaskInfo>) taskRepository.findAll();

        assertTrue("Not empty", result.size() > 0);
//        assertTrue("Should return the title", result.get(1).getTitle().equals(EXPECTED_TITLE));
    }
}
