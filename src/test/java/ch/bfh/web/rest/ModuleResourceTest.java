package ch.bfh.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import ch.bfh.Application;
import ch.bfh.domain.Module;
import ch.bfh.repository.ModuleRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ModuleResource REST controller.
 *
 * @see ModuleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ModuleResourceTest {
   private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static final Integer DEFAULT_TYPE = 0;
    private static final Integer UPDATED_TYPE = 1;
    
    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";
    
    private static final Integer DEFAULT_MIN_LESSONS = 0;
    private static final Integer UPDATED_MIN_LESSONS = 1;
    
    private static final Boolean DEFAULT_PASSED = false;
    private static final Boolean UPDATED_PASSED = true;
   private static final DateTime DEFAULT_YEAR = new DateTime(0L);
   private static final DateTime UPDATED_YEAR = new DateTime().withMillisOfSecond(0);
   private static final String DEFAULT_YEAR_STR = dateTimeFormatter.print(DEFAULT_YEAR);
    

    @Inject
    private ModuleRepository moduleRepository;

    private MockMvc restModuleMockMvc;

    private Module module;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModuleResource moduleResource = new ModuleResource();
        ReflectionTestUtils.setField(moduleResource, "moduleRepository", moduleRepository);
        this.restModuleMockMvc = MockMvcBuilders.standaloneSetup(moduleResource).build();
    }

    @Before
    public void initTest() {
        module = new Module();
        module.setType(DEFAULT_TYPE);
        module.setName(DEFAULT_NAME);
        module.setMinLessons(DEFAULT_MIN_LESSONS);
        module.setPassed(DEFAULT_PASSED);
        module.setYear(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void createModule() throws Exception {
        // Validate the database is empty
        assertThat(moduleRepository.findAll()).hasSize(0);

        // Create the Module
        restModuleMockMvc.perform(post("/app/rest/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(module)))
                .andExpect(status().isOk());

        // Validate the Module in the database
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(1);
        Module testModule = modules.iterator().next();
        assertThat(testModule.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testModule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModule.getMinLessons()).isEqualTo(DEFAULT_MIN_LESSONS);
        assertThat(testModule.getPassed()).isEqualTo(DEFAULT_PASSED);
        assertThat(testModule.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void getAllModules() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the modules
        restModuleMockMvc.perform(get("/app/rest/modules"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(module.getId().intValue()))
                .andExpect(jsonPath("$.[0].type").value(DEFAULT_TYPE))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.[0].minLessons").value(DEFAULT_MIN_LESSONS))
                .andExpect(jsonPath("$.[0].passed").value(DEFAULT_PASSED.booleanValue()))
                .andExpect(jsonPath("$.[0].year").value(DEFAULT_YEAR_STR));
    }

    @Test
    @Transactional
    public void getModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get the module
        restModuleMockMvc.perform(get("/app/rest/modules/{id}", module.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(module.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.minLessons").value(DEFAULT_MIN_LESSONS))
            .andExpect(jsonPath("$.passed").value(DEFAULT_PASSED.booleanValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR_STR));
    }

    @Test
    @Transactional
    public void getNonExistingModule() throws Exception {
        // Get the module
        restModuleMockMvc.perform(get("/app/rest/modules/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Update the module
        module.setType(UPDATED_TYPE);
        module.setName(UPDATED_NAME);
        module.setMinLessons(UPDATED_MIN_LESSONS);
        module.setPassed(UPDATED_PASSED);
        module.setYear(UPDATED_YEAR);
        restModuleMockMvc.perform(post("/app/rest/modules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(module)))
                .andExpect(status().isOk());

        // Validate the Module in the database
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(1);
        Module testModule = modules.iterator().next();
        assertThat(testModule.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testModule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModule.getMinLessons()).isEqualTo(UPDATED_MIN_LESSONS);
        assertThat(testModule.getPassed()).isEqualTo(UPDATED_PASSED);
        assertThat(testModule.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void deleteModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get the module
        restModuleMockMvc.perform(delete("/app/rest/modules/{id}", module.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Module> modules = moduleRepository.findAll();
        assertThat(modules).hasSize(0);
    }
}
