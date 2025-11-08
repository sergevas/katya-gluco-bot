package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryNotFoundException;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/entries")
public class EntriesApi {

    private static final Logger LOG = LoggerFactory.getLogger(EntriesApi.class);

    private final NsEntryMapper nsEntryMapper;
    private final NsEntryRepository nsEntryRepository;

    public EntriesApi(NsEntryMapper nsEntryMapper, NsEntryRepository nsEntryRepository) {
        this.nsEntryMapper = nsEntryMapper;
        this.nsEntryRepository = nsEntryRepository;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public void addEntries(@Valid @NotNull @RequestBody List<@Valid Entry> entries) {
        nsEntryRepository.storeNsEntries(nsEntryMapper.toNsEntries(entries));
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Entry> getEntries(EntryFilter entryFilter) {
        var nsEntryfilter = entryFilter.toNsEntryFilter();
        return nsEntryMapper.toEntries(nsEntryRepository.getAllNsEntries(nsEntryfilter));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Entry getEntryById(@Valid @PathVariable("id") Long id) {
        return nsEntryMapper.toModel(nsEntryRepository.getById(id).orElseThrow(() ->
                new NsEntryNotFoundException(String.format("Entry with id={%d} not found", id))));
    }

    @GetMapping(path = "/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public Entry getLatestEntry() {
        return nsEntryMapper.toModel(nsEntryRepository.getLatestNsEntry().orElseThrow(() ->
                new NsEntryNotFoundException("Latest entry not found")));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entry> getEntries(@RequestParam("find") String find, @RequestParam("count") BigDecimal count) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> remove(@RequestParam("find") String find, @RequestParam("count") BigDecimal count) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
