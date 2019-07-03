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

    private static final String EXPECTED_TITLE = "first task";
    private static final String EXPECTED_DESCRIPTION = "creating a task";
    private static final String EXPECTED_ASSIGNEE = "me";
    private static final String EXPECTED_STATUS = "available";

    @Before
    public void setup() throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(TaskInfo.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

//        dynamoDBMapper.batchDelete((List<TaskInfo>)taskRepository.findAll());
    }

    @Test
    public void readWriteTestCase() {
        TaskInfo dave = new TaskInfo(EXPECTED_TITLE, EXPECTED_DESCRIPTION, EXPECTED_ASSIGNEE, EXPECTED_STATUS);
        taskRepository.save(dave);

        List<TaskInfo> result = (List<TaskInfo>) taskRepository.findAll();

        assertTrue("Not empty", result.size() > 0);
        assertTrue("Should return the title", result.get(0).getTitle().equals(EXPECTED_TITLE));
    }
}
