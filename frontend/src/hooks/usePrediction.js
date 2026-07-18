import { useEffect, useState } from "react";

import api from "../api/axios";



export default function usePrediction(){


    const [prediction,setPrediction] = useState(null);

    const [loading,setLoading] = useState(true);

    const [error,setError] = useState(null);




    useEffect(()=>{


        const fetchPrediction = async()=>{


            try {


                const userId = 1; // temporary, will replace with JWT user id



                const response = await api.get(

                    `/prediction/next/${userId}`

                );



                setPrediction(

                    response.data

                );



            } catch(err){


                console.error(

                    "Prediction API error:",

                    err

                );


                setError(err);



            } finally {


                setLoading(false);


            }


        };



        fetchPrediction();



    },[]);





    return {


        prediction,

        loading,

        error


    };


}