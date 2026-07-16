import api from "./api";

export const getMyChildren = async () => {

    const response = await api.get(
        "/guardian/children"
    );

    return response.data;

};


export const getChildLocation = async (childId) => {

    const response = await api.get(
        `/guardian/child/${childId}/location`
    );

    return response.data;

};