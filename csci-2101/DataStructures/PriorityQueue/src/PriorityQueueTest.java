import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class PriorityQueueTest {

    @Test
    public void testAddAndRemoveJobs() {
        PriorityQueue<Job> priorityQueue = new PriorityQueue<Job>();

        Job highPriorityJob = new Job("High Priority Job", 1);
        Job mediumPriorityJob = new Job("Medium Priority Job", 2);
        Job lowPriorityJob = new Job("Low Priority Job", 3);

        priorityQueue.add(mediumPriorityJob);
        priorityQueue.add(highPriorityJob);
        priorityQueue.add(lowPriorityJob);

        // Ensure jobs are removed in the correct order
        assertEquals(highPriorityJob, priorityQueue.remove());
        assertEquals(mediumPriorityJob, priorityQueue.remove());
        assertEquals(lowPriorityJob, priorityQueue.remove());
    }

    @Test
    public void testAddJobsWithSamePriority() {
        PriorityQueue<Job> priorityQueue = new PriorityQueue<>();

        Job job1 = new Job("Job 1", 2);
        Job job2 = new Job("Job 2", 2);
        Job job3 = new Job("Job 3", 2);

        priorityQueue.add(job1);
        priorityQueue.add(job2);
        priorityQueue.add(job3);

        // Ensure jobs with the same priority are removed in the order they were added
        assertEquals(job1, priorityQueue.remove());
        assertEquals(job2, priorityQueue.remove());
        assertEquals(job3, priorityQueue.remove());
    }

    @Test
    public void testRemoveFromEmptyQueue() {
        PriorityQueue<Job> priorityQueue = new PriorityQueue<>();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            priorityQueue.remove(); // Attempting to remove from an empty queue should throw an exception
        });
    }

    @Test
    public void testAddAndRemoveMultipleJobs() {
        PriorityQueue<Job> priorityQueue = new PriorityQueue<>();

        Job jobA = new Job("Job A", 3);
        Job jobB = new Job("Job B", 1);
        Job jobC = new Job("Job C", 2);
        Job jobD = new Job("Job D", 4);

        priorityQueue.add(jobA);
        priorityQueue.add(jobB);
        priorityQueue.add(jobC);
        priorityQueue.add(jobD);

        // Ensure jobs are removed in the correct order
        assertEquals(jobB, priorityQueue.remove());
        assertEquals(jobC, priorityQueue.remove());
        assertEquals(jobA, priorityQueue.remove());
        assertEquals(jobD, priorityQueue.remove());
    }
}
