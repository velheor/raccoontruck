package com.velheor.internship.controllers;

import com.velheor.internship.BaseWebTest;
import com.velheor.internship.dto.AddressViewDTO;
import com.velheor.internship.exception.ErrorMessage;
import com.velheor.internship.mappers.AddressMapperImpl;
import com.velheor.internship.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.EntityNotFoundException;

import static com.velheor.internship.utils.TestUtils.ADDRESS1;
import static com.velheor.internship.utils.TestUtils.TEST_UUID;
import static com.velheor.internship.utils.TestWebUtils.ADDRESS_VIEW_DTO1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddressControllerTest extends BaseWebTest {
    private final String address_url = "/addresses/";
    private AddressService addressService;
    private AddressController addressController;

    public AddressControllerTest() {
        setUp(() -> {
            addressService = mock(AddressService.class);
            AddressMapperImpl addressMapper = new AddressMapperImpl();
            addressController = new AddressController(addressService, addressMapper);
            return addressController;
        });
    }

    @Test
    void findById() throws Exception {
        when(addressService.findById(ADDRESS1.getId())).thenReturn(ADDRESS1);
        mockMvc.perform(get(address_url + ADDRESS1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ADDRESS_VIEW_DTO1)));
    }

    @Test
    void findByIdBadRequest() throws Exception {
        mockMvc.perform(get(address_url + "notValidText")).andExpect(status().isBadRequest());
    }

    @Test
    void findByIdNotExists() throws Exception {
        when(addressService.findById(TEST_UUID)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get(address_url + TEST_UUID))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void update() throws Exception {
        when(addressService.save(ADDRESS1)).thenReturn(ADDRESS1);

        mockMvc.perform(put(address_url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ADDRESS_VIEW_DTO1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ADDRESS_VIEW_DTO1)));
    }

    @Test
    void updateThrowHandleMethodArgumentNotValid() throws Exception {
        AddressViewDTO address = new AddressViewDTO(ADDRESS_VIEW_DTO1);

        address.setStreetName(null);

        String responseBody = mockMvc.perform(put(address_url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(address)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andReturn().getResponse().getContentAsString();
        ErrorMessage actual = objectMapper.readValue(responseBody, ErrorMessage.class);
        int countOfErrors = 1;
        assertThat(actual.getErrors().size()).isEqualTo(countOfErrors);
    }

    @Test
    void save() throws Exception {
        when(addressService.save(ADDRESS1)).thenReturn(ADDRESS1);
        mockMvc.perform(post(address_url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ADDRESS_VIEW_DTO1)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ADDRESS_VIEW_DTO1)));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(delete(address_url + ADDRESS1.getId())).andExpect(status().isNoContent());

        verify(addressService).deleteById(ADDRESS1.getId());
    }
}