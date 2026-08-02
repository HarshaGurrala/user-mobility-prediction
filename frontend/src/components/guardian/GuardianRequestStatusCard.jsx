import React, { useEffect, useState, useRef } from "react";
import { getGuardianRequestStatus } from "../../services/guardianApi";

import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import {
    User,
    Mail,
    ShieldCheck,
    Clock,
    XCircle,
    CheckCircle
} from "lucide-react";


export default function GuardianRequestStatusCard() {


    const [requests, setRequests] = useState([]);

    const [loading, setLoading] = useState(false);


    const previousRequests = useRef([]);
    const LOGIN_TIME =
    useRef(Date.now());


    useEffect(() => {


        loadRequests();


        const interval = setInterval(() => {

            loadRequests();

        }, 5000);



        return () => {

            clearInterval(interval);

        };


    }, []);




    const loadRequests = async () => {


        try {


            setLoading(true);



            const data =
                await getGuardianRequestStatus();



            if(previousRequests.current.length > 0){


                data.forEach((newRequest)=>{


                    const oldRequest =
                        previousRequests.current.find(

                            item =>
                            item.request_id === newRequest.request_id

                        );



                    if(
                        oldRequest &&
                        oldRequest.status !== newRequest.status
                    ){


                        if(newRequest.status === "ACCEPTED"){


                            toast.success(

                                `${newRequest.user_name} accepted your request`

                            );


                        }



                        if(newRequest.status === "REJECTED"){


                            toast.error(

                                `${newRequest.user_name} rejected your request`

                            );


                        }


                    }


                });


            }



            const pendingRequests = data.filter(
                item => item.status === "PENDING"
            );


            setRequests(pendingRequests);


           const pendingOnly = data.filter(
                item => item.status === "PENDING"
            );


            setRequests(pendingOnly);


            previousRequests.current = data;

            const currentTime = Date.now();


            const fiveMinutes =
                5 * 60 * 1000;


            if(currentTime - LOGIN_TIME.current > fiveMinutes){

                setRequests([]);

                return;

            }



        }
      catch(error){

    console.error(
        "Request status error:",
        error.response?.data || error.message
    );


}
finally{

    setLoading(false);

}


    };





    const getStatus = (status)=>{


        if(status === "PENDING"){


            return {

                text:"Waiting for approval",

                color:"#f59e0b",

                icon:<Clock size={18}/>

            };


        }



        if(status === "ACCEPTED"){


            return {

                text:"Connected",

                color:"#22c55e",

                icon:<CheckCircle size={18}/>

            };


        }



        return {


            text:"Request rejected",

            color:"#ef4444",

            icon:<XCircle size={18}/>


        };


    };





    return (

        <>

        <ToastContainer />


        <div

        style={{

            width:"100%",

            background:"#111827",

            borderRadius:"20px",

            padding:"20px",

            marginTop:"20px",

            boxSizing:"border-box",

            border:
            "1px solid rgba(255,255,255,0.08)"

        }}

        >



            <div

            style={{

                display:"flex",

                alignItems:"center",

                gap:"10px",

                marginBottom:"18px"

            }}

            >


                <ShieldCheck

                    size={24}

                    color="#3b82f6"

                />



                <h3

                style={{

                    color:"white",

                    margin:0

                }}

                >

                    User Connection Status

                </h3>



                {
                    loading &&

                    <span

                    style={{

                        color:"#60a5fa",

                        fontSize:"12px"

                    }}

                    >

                    Updating...

                    </span>

                }


            </div>





            {

            requests.length === 0 ?

            (

                <p

                style={{

                    color:"#9ca3af"

                }}

                >

                No connection requests found

                </p>


            )


            :


            requests.map((item)=>(


                <div

                key={item.request_id}

                style={{

                    width:"100%",

                    background:"#1f2937",

                    borderRadius:"16px",

                    padding:"16px",

                    marginBottom:"12px",

                    boxSizing:"border-box"

                }}

                >



                    <div

                    style={{

                        display:"flex",

                        alignItems:"center",

                        gap:"10px"

                    }}

                    >


                        <User

                        size={22}

                        color="#60a5fa"

                        />



                        <h4

                        style={{

                            color:"white",

                            margin:0

                        }}

                        >

                        {item.user_name}

                        </h4>


                    </div>





                    <p

                    style={{

                        color:"#9ca3af"

                    }}

                    >

                    SafePath ID : {item.safe_path_id}

                    </p>




                    <div

                    style={{

                        display:"flex",

                        alignItems:"center",

                        gap:"8px"

                    }}

                    >


                        <Mail

                        size={16}

                        color="#9ca3af"

                        />



                        <span

                        style={{

                            color:"#d1d5db"

                        }}

                        >

                        {item.email}

                        </span>


                    </div>





                    {

                    (()=>{


                        const status =
                        getStatus(item.status);



                        return (

                            <div

                            style={{

                                marginTop:"15px",

                                display:"flex",

                                alignItems:"center",

                                gap:"8px",

                                color:
                                status.color,

                                fontWeight:"600"

                            }}

                            >

                            {status.icon}

                            {status.text}


                            </div>

                        )


                    })()


                    }



                </div>


            ))

            }



        </div>


        </>

    );

}