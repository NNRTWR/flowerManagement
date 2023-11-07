package ku.cs.flowerManagement;

import ku.cs.flowerManagement.entity.Flower;
import ku.cs.flowerManagement.model.FlowerRequest;
import ku.cs.flowerManagement.repository.FlowerRepository;
import ku.cs.flowerManagement.service.FlowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.any;

public class FlowerServiceTest {

    @InjectMocks
    private FlowerService flowerService;

    @Mock
    private FlowerRepository flowerRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFlower() {
        List<Flower> flowers = List.of(new Flower(), new Flower());
        when(flowerRepository.findAll()).thenReturn(flowers);

        List<FlowerRequest> result = flowerService.getFlowers();

        assertEquals(flowers.size(), result.size());
    }

    @Test
    public void testGetAllFlower() {
        List<Flower> flowers = List.of(new Flower(), new Flower());
        when(flowerRepository.findAll()).thenReturn(flowers);

        List<Flower> result = flowerService.getAllFlower();

        assertEquals(flowers.size(), result.size());
    }

    @Test
    public void testGetFlowerWithPagination() {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<Flower> flowerPage = mock(Page.class);
        when(flowerRepository.findAll(pageable)).thenReturn(flowerPage);

        Page<Flower> result = flowerService.getFlowers(page, size);

        assertEquals(flowerPage, result);

    }

    @Test
    public void testGetOneFlower() {
        int flowerId = 1;
        Flower flower = new Flower();
        when(flowerRepository.findById(flowerId)).thenReturn(Optional.of(flower));

        Flower result = flowerService.getOneFlower(flowerId);

        assetNotNull(result);
    }


    private void assetNotNull(Flower result) {
    }



}
