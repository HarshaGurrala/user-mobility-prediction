import { motion } from "framer-motion";

export default function AuthCard({children,title,subtitle}) {

  return (

    <motion.div

      initial={{
        opacity:0,
        y:40
      }}

      animate={{
        opacity:1,
        y:0
      }}

      transition={{
        duration:.7
      }}

      className="
      w-full
      max-w-md
      rounded-[35px]
      border
      border-white/10
      bg-white/5
      backdrop-blur-3xl
      p-10
      shadow-2xl
      "

    >

      <div className="text-center mb-10">

        <div
        className="
        mx-auto
        w-14
        h-14
        rounded-full
        bg-white
        flex
        items-center
        justify-center
        text-black
        font-bold
        text-xl
        "
        >

          M

        </div>


        <h1
        className="
        text-3xl
        font-bold
        text-white
        mt-6
        "
        >

          {title}

        </h1>


        <p
        className="
        text-gray-400
        mt-3
        "
        >

          {subtitle}

        </p>


      </div>


      {children}


    </motion.div>

  );
}