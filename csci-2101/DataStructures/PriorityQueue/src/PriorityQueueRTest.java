import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class PriorityQueueRTest {

    @Test
    public void testAddAndRemoveJobs() {
        PriorityQueueR<Job> priorityQueueR = new PriorityQueueR<Job>();

        Job highPriorityJob = new Job("High Priority Job", 1);
        Job mediumPriorityJob = new Job("Medium Priority Job", 2);
        Job lowPriorityJob = new Job("Low Priority Job", 3);

        priorityQueueR.add(mediumPriorityJob);
        priorityQueueR.add(highPriorityJob);
        priorityQueueR.add(lowPriorityJob);

        // Ensure jobs are removed in the correct order
        assertEquals(highPriorityJob, priorityQueueR.remove());
        assertEquals(mediumPriorityJob, priorityQueueR.remove());
        assertEquals(lowPriorityJob, priorityQueueR.remove());
    }

    @Test
    public void testAddJobsWithSamePriority() {
        PriorityQueueR<Job> priorityQueueR = new PriorityQueueR<>();

        Job job1 = new Job("Job 1", 2);
        Job job2 = new Job("Job 2", 2);
        Job job3 = new Job("Job 3", 2);

        priorityQueueR.add(job1);
        priorityQueueR.add(job2);
        priorityQueueR.add(job3);

        // Ensure jobs with the same priority are removed in the order they were added
        assertEquals(job1, priorityQueueR.remove());
        assertEquals(job2, priorityQueueR.remove());
        assertEquals(job3, priorityQueueR.remove());
    }

    @Test
    public void testRemoveFromEmptyQueue() {
        PriorityQueueR<Job> priorityQueueR = new PriorityQueueR<>();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            priorityQueueR.remove(); // Attempting to remove from an empty queue should throw an exception
        });
    }

    @Test
    public void testAddAndRemoveMultipleJobs() {
        PriorityQueueR<Job> priorityQueueR = new PriorityQueueR<>();

        Job jobA = new Job("Job A", 3);
        Job jobB = new Job("Job B", 1);
        Job jobC = new Job("Job C", 2);
        Job jobD = new Job("Job D", 4);

        priorityQueueR.add(jobA);
        priorityQueueR.add(jobB);
        priorityQueueR.add(jobC);
        priorityQueueR.add(jobD);

        // Ensure jobs are removed in the correct order
        assertEquals(jobB, priorityQueueR.remove());
        assertEquals(jobC, priorityQueueR.remove());
        assertEquals(jobA, priorityQueueR.remove());
        assertEquals(jobD, priorityQueueR.remove());
    }
}
