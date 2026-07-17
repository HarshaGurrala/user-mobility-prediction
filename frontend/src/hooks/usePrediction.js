// import { useEffect, useState } from "react";

// import { getPrediction } from "../services/predictionService";

// export default function usePrediction() {

//   const [prediction, setPrediction] = useState(null);

//   useEffect(() => {

//     loadPrediction();

//     const interval = setInterval(() => {

//       loadPrediction();

//     }, 10000);

//     return () => clearInterval(interval);

//   }, []);

//   async function loadPrediction() {

//     try {

//       const data = await getPrediction();

//       setPrediction(data);

//     }

//     catch (err) {

//       console.log(err);

//     }

//   }

//   return prediction;

// }
import { useEffect, useState } from "react";
import { getPrediction } from "../services/predictionService";


export default function usePrediction() {

  const [prediction, setPrediction] = useState(null);


  useEffect(() => {

    async function loadPrediction() {

      try {

        const data = await getPrediction();

        setPrediction(data);

      } catch (error) {

        console.log(
          "Prediction Error:",
          error
        );

      }

    }


    loadPrediction();


    const interval = setInterval(() => {

      loadPrediction();

    }, 10000);


    return () => clearInterval(interval);


  }, []);


  return prediction;

}