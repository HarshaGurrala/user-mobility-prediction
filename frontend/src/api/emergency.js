import api from "./axios";

export const getContacts = () =>
    api.get("/emergency/all");

export const addContact = (data) =>
    api.post("/emergency/add", data);

export const deleteContact = (id) =>
    api.delete(`/emergency/${id}`);