// src/api/supplyApi.js
import axios from 'axios';

// Base URL for all supply-related endpoints.
const BASE = '/api/supplies';

export function getAllSupplies() {
    return axios.get(BASE);
}

export function getSupplyById(id) {
    return axios.get(`${BASE}/${id}`);
}

export function createSupply(item) {
    return axios.post(BASE, item);
}

export function updateSupply(id, item) {
    return axios.put(`${BASE}/${id}`, item);
}

export function deleteSupply(id) {
    return axios.delete(`${BASE}/${id}`);
}

export function searchSupplies(params) {
    // params is an object { name: 'foo' } or { category: 'Beans' }
    return axios.get(`${BASE}/search`, { params });
}

export function updateQuantity(id, quantity) {
    return axios.patch(`${BASE}/${id}/quantity`, { quantity });
}

export function getReorderList() {
    return axios.get(`${BASE}/reorder`);
}

export function importCsv(file) {
    const data = new FormData();
    data.append('file', file);
    return axios.post(`${BASE}/import`, data, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
}
