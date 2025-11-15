package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import dev.sergevas.tool.katya.gluco.bot.web.boundary.SortSpecConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static dev.sergevas.tool.katya.gluco.bot.nightscout.support.NightscoutTestData.NS_ENTRY_1;
import static dev.sergevas.tool.katya.gluco.bot.nightscout.support.NightscoutTestData.NS_ENTRY_2;
import static dev.sergevas.tool.katya.gluco.bot.security.AuthenticationService.API_SECRET_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EntriesApi.class)
@Import({
        SortSpecConverter.class,
        NsEntryMapper.class
})
@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class EntriesApiTest {

    @MockitoBean
    private NsEntryRepository nsEntryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenExistentNsEntries_whenGetFilteredWithMultipleSort_thenShouldReturnSuccessfully() throws Exception {
        when(nsEntryRepository.getNsEntries(any(NsEntryFilter.class))).thenReturn(List.of(NS_ENTRY_1, NS_ENTRY_2));

        mockMvc.perform(get("/api/v1/entries/all?sort=dateString,desc")
//                        .param("sort", "dateString,desc")
//                        .param("sort", "device,asc")
//                        .param("page", "0")
//                        .param("size", "10")
                        .header(API_SECRET_HEADER, "test-api-secret")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
//                .andExpect(jsonPath("$[0].date").value(NS_ENTRY_2.getDate()))
//                .andExpect(jsonPath("$[1].date").value(NS_ENTRY_1.getDate()));
    }
}