package seo.dale.raddit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest{

    @Mock
    private TopicRepository repository;

    @InjectMocks
    private TopicService service;

    @Test
    public void testContribute() {
        String toAdd = "test topic content";
        when(repository.save(any(Topic.class))).thenReturn(new Topic(toAdd));
        service.contribute(toAdd);
        verify(repository).save(any(Topic.class));
    }

    @Test
    public void testUpvote() {
        Topic mockTopic = new Topic("mock topic content", 20, 0);
        String mockId = mockTopic.getId();
        when(repository.findOne(mockId)).thenReturn(mockTopic);

        assertThat(mockTopic.getUps())
                .as("Before: should be as it was.")
                .isEqualTo(20);

        service.upvote(mockId);

        assertThat(mockTopic.getUps())
                .as("After: should be one more.")
                .isEqualTo(21);

        verify(repository).findOne(mockId);
    }

    @Test
    public void testDownvote() {
        Topic mockTopic = new Topic("mock topic content", 0, 30);
        String mockId = mockTopic.getId();
        when(repository.findOne(mockId)).thenReturn(mockTopic);

        assertThat(mockTopic.getDowns())
                .as("Before: should be as it was.")
                .isEqualTo(30);

        service.downvote(mockId);

        assertThat(mockTopic.getDowns())
                .as("After: should be one more.")
                .isEqualTo(31);

        verify(repository).findOne(mockId);
    }

    @Test
	public void testFindTop20() {
	    List<Topic> mockTop20s = IntStream.range(0, 20)
			    .boxed()
			    .map(n -> new Topic("test", n, n))
			    .collect(Collectors.toList());
    	when(repository.findTopN(20)).thenReturn(mockTop20s);

	    assertThat(service.findTop20())
			    .isEqualTo(mockTop20s);

    	verify(repository).findTopN(20);
    }

}