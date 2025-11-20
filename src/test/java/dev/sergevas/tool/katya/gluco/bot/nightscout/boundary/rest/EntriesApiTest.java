package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;

import static dev.sergevas.tool.katya.gluco.bot.nightscout.support.NightscoutTestData.ENTRY_HAL_PAGED_REPLY;
import static dev.sergevas.tool.katya.gluco.bot.nightscout.support.NightscoutTestData.NS_ENTRY_1;
import static dev.sergevas.tool.katya.gluco.bot.nightscout.support.NightscoutTestData.NS_ENTRY_2;
import static dev.sergevas.tool.katya.gluco.bot.security.AuthenticationService.API_SECRET_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EntriesApi.class)
@Import({
        NsEntryAssembler.class,
        SortFieldMapper.class
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
        when(nsEntryRepository.getNsEntries(any(NsEntryFilter.class))).thenReturn(
                new PageImpl<>(List.of(NS_ENTRY_1, NS_ENTRY_2)));

        mockMvc.perform(get("/api/v1/entries/all")
                        .param("sort", "dateString,desc")
                        .param("sort", "device,asc")
                        .param("page", "1")
                        .param("size", "10")
                        .param("fromDateString", "2025-09-01T11:13:59.000+03:00")
                        .param("toDateString", "2025-09-02T21:00:00.000+03:00")
                        .param("device", "3MH01DTCMC4")
                        .param("direction", "FortyFiveDown")
                        .param("dateString", "2025-09-03T22:01:05.000+03:00")
                        .header(API_SECRET_HEADER, "test-api-secret")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(ENTRY_HAL_PAGED_REPLY));
//                .andExpect(jsonPath("$.length()").value(2));
//                .andExpect(jsonPath("$[0].date").value(NS_ENTRY_2.getDate()))
//                .andExpect(jsonPath("$[1].date").value(NS_ENTRY_1.getDate()));

        verify(nsEntryRepository).getNsEntries(new NsEntryFilter(
                "3MH01DTCMC4",
                "FortyFiveDown",
                OffsetDateTime.parse("2025-09-03T22:01:05+03:00"),
                OffsetDateTime.parse("2025-09-01T11:13:59+03:00"),
                OffsetDateTime.parse("2025-09-02T21:00+03:00"),
                PageRequest.of(1, 10,
                        Sort.by(Sort.Order.desc("sgvTime"), Sort.Order.asc("device")))));
    }


}
