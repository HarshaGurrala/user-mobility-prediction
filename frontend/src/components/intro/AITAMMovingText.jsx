import { motion } from "framer-motion";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
/*
|--------------------------------------------------------------------------
| AITAM
| Lowercase: a i t a m
|--------------------------------------------------------------------------
*/

const aitamLetters = [
  // lowercase a
  {
    id: "a",
    d: `
      M 48 52
      C 48 42 42 36 34 36
      C 25 36 19 43 19 52
      C 19 61 25 68 34 68
      C 42 68 48 62 48 52
      M 48 36
      L 48 68
    `,
  },

  // lowercase i
  {
    id: "i",
    d: `
      M 78 35
      L 78 71
      M 78 23
      L 78 23
    `,
  },

  // lowercase t
  {
    id: "t",
    d: `
      M 108 24
      L 108 71
      M 96 38
      L 120 38
    `,
  },

  // lowercase a
  {
    id: "a2",
    d: `
      M 174 52
      C 174 42 168 36 160 36
      C 151 36 145 43 145 52
      C 145 61 151 68 160 68
      C 168 68 174 62 174 52
      M 174 36
      L 174 68
    `,
  },

  // lowercase m
  {
    id: "m",
    d: `
      M 204 71
      L 204 35
      M 204 42
      C 211 33 221 33 228 42
      L 228 71
      M 228 42
      C 235 33 245 33 252 42
      L 252 71
    `,
  },
];
/*
|--------------------------------------------------------------------------
| ADITYA
|--------------------------------------------------------------------------
*/

const aditya = [
  {
    id: "A",
    d: "M 0 30 L 8 8 L 16 30 M 3 22 L 13 22",
  },

  {
    id: "D",
    d: "M 24 8 L 24 30 L 34 30 C 41 30 45 25 45 19 C 45 13 41 8 34 8 Z",
  },

  {
    id: "I",
    d: "M 53 8 L 53 30",
  },

  {
    id: "T",
    d: "M 61 8 L 85 8 M 73 8 L 73 30",
  },

  {
    id: "Y",
    d: "M 94 8 L 102 19 L 110 8 M 102 19 L 102 30",
  },

  {
    id: "A2",
    d: "M 119 30 L 127 8 L 135 30 M 122 22 L 132 22",
  },
];

/*
|--------------------------------------------------------------------------
| INSTITUTE
|--------------------------------------------------------------------------
*/

const institute = [
  {
    id: "I",
    d: "M 0 8 L 0 30",
  },

  {
    id: "N",
    d: "M 10 30 L 10 8 L 32 30 L 32 8",
  },

{
  id: "S",
  d: `
    M 60 11
    C 57 9 54 8 50 8
    C 45 8 42 10 42 14
    C 42 18 46 19 51 20
    C 56 21 60 23 60 27
    C 60 30 57 32 53 32
    C 49 32 45 30 42 28
  `,
},

  {
    id: "T",
    d: "M 65 8 L 89 8 M 77 8 L 77 30",
  },

  {
    id: "I2",
    d: "M 99 8 L 99 30",
  },

  {
    id: "T2",
    d: "M 107 8 L 131 8 M 119 8 L 119 30",
  },

  {
    id: "U",
    d: `
      M 141 8
      L 141 23
      C 141 28 145 30 151 30
      C 157 30 161 28 161 23
      L 161 8
    `,
  },

  {
    id: "T3",
    d: "M 171 8 L 195 8 M 183 8 L 183 30",
  },

  {
    id: "E",
    d: `
      M 205 8 L 205 30
      M 205 8 L 227 8
      M 205 19 L 224 19
      M 205 30 L 227 30
    `,
  },
];

/*
|--------------------------------------------------------------------------
| OF
|--------------------------------------------------------------------------
*/

const of = [
  {
    id: "O",
    d: `
      M 0 19
      C 0 12 5 8 12 8
      C 19 8 24 12 24 19
      C 24 26 19 30 12 30
      C 5 30 0 26 0 19
    `,
  },

  {
    id: "F",
    d: `
      M 34 30
      L 34 8
      L 57 8
      M 34 19
      L 53 19
    `,
  },
];

/*
|--------------------------------------------------------------------------
| TECHNOLOGY
|--------------------------------------------------------------------------
*/

const technology = [
  {
    id: "T",
    d: "M 0 8 L 24 8 M 12 8 L 12 30",
  },

  {
    id: "E",
    d: `
      M 34 8 L 34 30
      M 34 8 L 56 8
      M 34 19 L 53 19
      M 34 30 L 56 30
    `,
  },

  {
    id: "C",
    d: `
      M 84 11
      C 80 8 76 8 72 8
      C 64 8 59 12 59 19
      C 59 26 64 30 72 30
      C 76 30 80 30 84 27
    `,
  },

  {
    id: "H",
    d: `
      M 94 8 L 94 30
      M 116 8 L 116 30
      M 94 19 L 116 19
    `,
  },

  {
    id: "N",
    d: "M 126 30 L 126 8 L 148 30 L 148 8",
  },

  {
    id: "O",
    d: `
      M 158 19
      C 158 12 163 8 170 8
      C 177 8 182 12 182 19
      C 182 26 177 30 170 30
      C 163 30 158 26 158 19
    `,
  },

  {
    id: "L",
    d: "M 192 8 L 192 30 L 214 30",
  },

  {
    id: "O2",
    d: `
      M 224 19
      C 224 12 229 8 236 8
      C 243 8 248 12 248 19
      C 248 26 243 30 236 30
      C 229 30 224 26 224 19
    `,
  },

  {
    id: "G",
    d: `
      M 278 11
      C 274 8 270 8 266 8
      C 258 8 253 12 253 19
      C 253 26 258 30 266 30
      C 272 30 276 28 278 25
      L 278 20
      L 267 20
    `,
  },

  {
    id: "Y",
    d: `
      M 286 8
      L 294 19
      L 302 8
      M 294 19
      L 294 30
    `,
  },
];

/*
|--------------------------------------------------------------------------
| AND
|--------------------------------------------------------------------------
*/

const and = [
  {
    id: "A",
    d: "M 0 30 L 8 8 L 16 30 M 3 22 L 13 22",
  },

  {
    id: "N",
    d: "M 25 30 L 25 8 L 47 30 L 47 8",
  },

  {
    id: "D",
    d: `
      M 57 8
      L 57 30
      L 67 30
      C 74 30 78 25 78 19
      C 78 13 74 8 67 8
      Z
    `,
  },
];

/*
|--------------------------------------------------------------------------
| MANAGEMENT
|--------------------------------------------------------------------------
*/

const management = [
  {
    id: "M",
    d: "M 0 30 L 0 8 L 12 22 L 24 8 L 24 30",
  },

  {
    id: "A",
    d: "M 34 30 L 42 8 L 50 30 M 37 22 L 47 22",
  },

  {
    id: "N",
    d: "M 60 30 L 60 8 L 82 30 L 82 8",
  },

  {
    id: "A2",
    d: "M 92 30 L 100 8 L 108 30 M 95 22 L 105 22",
  },

  {
    id: "G",
    d: `
      M 136 11
      C 132 8 128 8 124 8
      C 116 8 111 12 111 19
      C 111 26 116 30 124 30
      C 130 30 134 28 136 25
      L 136 20
      L 125 20
    `,
  },

  {
    id: "E",
    d: `
      M 146 8 L 146 30
      M 146 8 L 168 8
      M 146 19 L 165 19
      M 146 30 L 168 30
    `,
  },

  {
    id: "M2",
    d: "M 178 30 L 178 8 L 190 22 L 202 8 L 202 30",
  },

  {
    id: "E2",
    d: `
      M 212 8 L 212 30
      M 212 8 L 234 8
      M 212 19 L 231 19
      M 212 30 L 234 30
    `,
  },

  {
    id: "N2",
    d: "M 244 30 L 244 8 L 266 30 L 266 8",
  },

  {
    id: "T",
    d: "M 276 8 L 300 8 M 288 8 L 288 30",
  },
];

/*
|--------------------------------------------------------------------------
| ANIMATED SVG STROKE
|--------------------------------------------------------------------------
*/

function AnimatedStroke({ d, index, width = 2 }) {
  return (
    <>
      {/* =====================================================
          RED / BLUE ENERGY GLOW
          Glow stays ONLY on the actual letter stroke
      ====================================================== */}

      <motion.path
        d={d}
        fill="none"
        stroke="#b91c1c"
        strokeWidth={width + 5}
        strokeLinecap="round"
        strokeLinejoin="round"
        pathLength="1"
        initial={{
          pathLength: 0,
          opacity: 0,
        }}
        animate={{
          pathLength: [0, 1, 1, 0],
          opacity: [0, 1, 0.85, 0],
        }}
        transition={{
          duration: 3,
          delay: index * 0.08,
          repeat: Infinity,
          repeatDelay: 0.5,
          ease: "easeInOut",
        }}
        className="blur-[5px]"
      />

      {/* =====================================================
          MAIN SILVER / WHITE LINE
      ====================================================== */}

      <motion.path
        d={d}
        fill="none"
        stroke="#e5e7eb"
        strokeWidth={width}
        strokeLinecap="round"
        strokeLinejoin="round"
        pathLength="1"
        initial={{
          pathLength: 0,
          opacity: 0,
        }}
        animate={{
          pathLength: [0, 1, 1, 0],
          opacity: [0, 1, 1, 0],
        }}
        transition={{
          duration: 3,
          delay: index * 0.08,
          repeat: Infinity,
          repeatDelay: 0.5,
          ease: "easeInOut",
        }}
      />

      {/* =====================================================
          ROYAL BLUE SECONDARY ENERGY
      ====================================================== */}

      <motion.path
        d={d}
        fill="none"
        stroke="#2563eb"
        strokeWidth={width * 0.45}
        strokeLinecap="round"
        strokeLinejoin="round"
        pathLength="1"
        initial={{
          pathLength: 0,
          opacity: 0,
        }}
        animate={{
          pathLength: [0, 1, 1, 0],
          opacity: [0, 0.8, 0.7, 0],
        }}
        transition={{
          duration: 3,
          delay: index * 0.08 + 0.1,
          repeat: Infinity,
          repeatDelay: 0.5,
          ease: "easeInOut",
        }}
      />

      {/* =====================================================
          WHITE ENERGY POINT
      ====================================================== */}

      <motion.path
        d={d}
        fill="none"
        stroke="#ffffff"
        strokeWidth={width * 0.7}
        strokeLinecap="round"
        pathLength="1"
        strokeDasharray="0.035 0.965"
        initial={{
          strokeDashoffset: 1,
          opacity: 0,
        }}
        animate={{
          strokeDashoffset: [1, 0],
          opacity: [0, 1, 1, 0],
        }}
        transition={{
          duration: 2,
          delay: index * 0.08 + 0.8,
          repeat: Infinity,
          ease: "linear",
        }}
      />
    </>
  );
}

/*
|--------------------------------------------------------------------------
| REUSABLE ANIMATED WORD
|--------------------------------------------------------------------------
*/

function AnimatedWord({
  letters,
  startIndex,
  viewBox,
  className = "",
}) {
  return (
    <svg
      viewBox={viewBox}
      className={className}
      preserveAspectRatio="xMinYMid meet"
    >
      {letters.map((letter, index) => (
        <AnimatedStroke
          key={letter.id}
          d={letter.d}
          index={startIndex + index}
        />
      ))}
    </svg>
  );
}

/*
|--------------------------------------------------------------------------
| MAIN COMPONENT
|--------------------------------------------------------------------------
*/
export default function AITAMMovingText({ onComplete }) {
  useEffect(() => {
    if (!onComplete) return;

    const timer = setTimeout(() => {
      onComplete();
    }, 5000);

    return () => clearTimeout(timer);
  }, [onComplete]);

  return (
    <main className="relative min-h-screen w-full overflow-hidden bg-black">

   <AnimatedJubileeText />

      <div className="pointer-events-none absolute inset-0">

       

        {/* Dark overlay so animated text stays visible */}
        <div className="absolute inset-0 bg-black/55" />

      </div>


      {/* =====================================================
          ANIMATED TEXT CONTENT
      ====================================================== */}

      <div className="relative z-10 flex min-h-screen w-full items-center justify-center">

        <div className="w-full max-w-[1400px] px-8 md:px-14 lg:px-20">

          {/* =================================================
              AITAM
          ================================================= */}

          <AnimatedWord
  letters={aitamLetters}
  startIndex={0}
  viewBox="0 0 270 180"
  className="h-[640px] w-[924px] translate-y-[300px]"
/>


          {/* =================================================
              LINE + INSTITUTE NAME
          ================================================= */}

          <div className="-mt-[30px]">

            {/* =================================================
                ONE CONTINUOUS HORIZONTAL LINE
            ================================================= */}

            <div className="relative h-[2px] w-full overflow-hidden">

              {/* Base line */}
              <div className="absolute inset-0 bg-white/30" />

              {/* Red moving energy */}
              <motion.div
                className="
                  absolute
                  left-0
                  top-0
                  h-full
                  w-[180px]
                  bg-gradient-to-r
                  from-transparent
                  via-red-600
                  to-transparent
                "
                animate={{
                  x: ["-180px", "calc(100vw + 180px)"],
                }}
                transition={{
                  duration: 4,
                  repeat: Infinity,
                  ease: "linear",
                }}
              />

              {/* Blue moving energy */}
              <motion.div
                className="
                  absolute
                  left-0
                  top-0
                  h-full
                  w-[100px]
                  bg-gradient-to-r
                  from-transparent
                  via-blue-500
                  to-transparent
                "
                animate={{
                  x: ["-100px", "calc(100vw + 100px)"],
                }}
                transition={{
                  duration: 3,
                  repeat: Infinity,
                  ease: "linear",
                  delay: 0.4,
                }}
              />

            </div>


            {/* =================================================
                INSTITUTE NAME
            ================================================= */}

            <div className="mt-3 flex w-full items-start">

              {/* ADITYA */}
              <AnimatedWord
                letters={aditya}
                startIndex={5}
                viewBox="0 0 135 40"
                className="h-[40px] w-[170px] shrink-0"
              />

              {/* 2 spaces */}
              <div className="w-[16px] shrink-0" />


              {/* INSTITUTE */}
              <AnimatedWord
                letters={institute}
                startIndex={11}
                viewBox="0 0 230 45"
                className="h-[40px] w-[230px] shrink-0"
              />

              {/* 2 spaces */}
              <div className="w-[12px] shrink-0" />


              {/* OF */}
              <AnimatedWord
                letters={of}
                startIndex={21}
                viewBox="0 0 57 40"
                className="h-[40px] w-[80px] shrink-0"
              />

              {/* 1 space */}
              <div className="w-[8px] shrink-0" />


              {/* TECHNOLOGY */}
              <AnimatedWord
                letters={technology}
                startIndex={23}
                viewBox="0 0 305 40"
                className="h-[40px] w-[340px] shrink-0"
              />

              {/* 2 spaces */}
              <div className="w-[12px] shrink-0" />


              {/* AND */}
              <AnimatedWord
                letters={and}
                startIndex={33}
                viewBox="0 0 80 40"
                className="h-[40px] w-[110px] shrink-0"
              />

              {/* 2 spaces */}
              <div className="w-[12px] shrink-0" />


              {/* MANAGEMENT */}
              <AnimatedWord
                letters={management}
                startIndex={36}
                viewBox="0 0 305 40"
                className="h-[40px] w-[305px] shrink-0"
              />

            </div>

          </div>

        </div>

      </div>

    </main>
  );
}

function AnimatedJubileeText() {
  return (
   <div className="pointer-events-none absolute inset-0 flex items-center justify-center -translate-y-[150px]">

      <svg
        viewBox="0 0 1000 500"
        className="h-[520px] w-[1000px] max-w-[90vw] overflow-visible"
      >

        {/* =====================================================
            25
        ====================================================== */}

        {/* 2 */}
        <motion.text
          x="500"
          y="245"
          textAnchor="middle"
          fontSize="260"
          fontWeight="700"
          fontFamily="Arial, Helvetica, sans-serif"
          fill="none"
          stroke="#cbd5e1"
          strokeWidth="3"
          strokeLinecap="round"
          strokeLinejoin="round"
          pathLength="1"
          initial={{
            strokeDasharray: "0 1",
            strokeDashoffset: 1,
            opacity: 0,
          }}
          animate={{
            strokeDasharray: ["0 1", "1 0", "1 0", "0 1"],
            strokeDashoffset: [1, 0, 0, -1],
            opacity: [0, 1, 0.85, 0],
          }}
          transition={{
            duration: 5,
            repeat: Infinity,
            ease: "easeInOut",
          }}
        >
          25
        </motion.text>

        {/* Blue energy outline */}
        <motion.text
          x="500"
          y="245"
          textAnchor="middle"
          fontSize="260"
          fontWeight="700"
          fontFamily="Arial, Helvetica, sans-serif"
          fill="none"
          stroke="#2563eb"
          strokeWidth="8"
          strokeLinecap="round"
          strokeLinejoin="round"
          pathLength="1"
          initial={{
            pathLength: 0,
            opacity: 0,
          }}
          animate={{
            pathLength: [0, 1, 1, 0],
            opacity: [0, 0.8, 0.6, 0],
          }}
          transition={{
            duration: 5,
            delay: 0.15,
            repeat: Infinity,
            ease: "easeInOut",
          }}
          className="blur-[5px]"
        >
          25
        </motion.text>

        {/* Moving white highlight */}
        <motion.text
          x="500"
          y="245"
          textAnchor="middle"
          fontSize="260"
          fontWeight="700"
          fontFamily="Arial, Helvetica, sans-serif"
          fill="none"
          stroke="white"
          strokeWidth="2"
          strokeLinecap="round"
          pathLength="1"
          strokeDasharray="0.025 0.975"
          initial={{
            strokeDashoffset: 1,
            opacity: 0,
          }}
          animate={{
            strokeDashoffset: [1, 0],
            opacity: [0, 1, 1, 0],
          }}
          transition={{
            duration: 2.5,
            delay: 1,
            repeat: Infinity,
            ease: "linear",
          }}
        >
          25
        </motion.text>


        {/* =====================================================
            SILVER JUBILEE
        ====================================================== */}

        {/* Main silver line-art */}
        <motion.text
          x="500"
          y="365"
          textAnchor="middle"
          fontSize="78"
          fontWeight="700"
          letterSpacing="12"
          fontFamily="Arial, Helvetica, sans-serif"
          fill="none"
          stroke="#e2e8f0"
          strokeWidth="2"
          strokeLinecap="round"
          strokeLinejoin="round"
          pathLength="1"
          initial={{
            pathLength: 0,
            opacity: 0,
          }}
          animate={{
            pathLength: [0, 1, 1, 0],
            opacity: [0, 1, 0.85, 0],
          }}
          transition={{
            duration: 3,
            delay: 1.2,
            repeat: Infinity,
            ease: "easeInOut",
          }}
        >
          SILVER JUBILEE
        </motion.text>

        {/* Red energy */}
        <motion.text
          x="500"
          y="365"
          textAnchor="middle"
          fontSize="78"
          fontWeight="700"
          letterSpacing="12"
          fontFamily="Arial, Helvetica, sans-serif"
          fill="none"
          stroke="#dc2626"
          strokeWidth="7"
          strokeLinecap="round"
          strokeLinejoin="round"
          pathLength="1"
          initial={{
            pathLength: 0,
            opacity: 0,
          }}
          animate={{
            pathLength: [0, 1, 1, 0],
            opacity: [0, 0.75, 0.55, 0],
          }}
          transition={{
            duration: 3,
            delay: 1,
            repeat: Infinity,
            ease: "easeInOut",
          }}
          className="blur-[5px]"
        >
          SILVER JUBILEE
        </motion.text>

        {/* White moving highlight */}
        <motion.text
          x="500"
          y="365"
          textAnchor="middle"
          fontSize="78"
          fontWeight="700"
          letterSpacing="12"
          fontFamily="Arial, Helvetica, sans-serif"
          fill="none"
          stroke="white"
          strokeWidth="1.8"
          strokeLinecap="round"
          pathLength="1"
          strokeDasharray="0.025 0.975"
          initial={{
            strokeDashoffset: 1,
            opacity: 0,
          }}
          animate={{
            strokeDashoffset: [1, 0],
            opacity: [0, 1, 1, 0],
          }}
          transition={{
            duration: 3,
            delay: 2,
            repeat: Infinity,
            ease: "linear",
          }}
        >
          SILVER JUBILEE
        </motion.text>


        {/* =====================================================
            SMALL EST. 2001
        ====================================================== */}

        <motion.text
          x="500"
          y="420"
          textAnchor="middle"
          fontSize="28"
          letterSpacing="8"
          fontFamily="Arial, Helvetica, sans-serif"
          fill="none"
          stroke="#94a3b8"
          strokeWidth="1.2"
          pathLength="1"
          initial={{
            pathLength: 0,
            opacity: 0,
          }}
          animate={{
            pathLength: [0, 1, 1, 0],
            opacity: [0, 0.8, 0.7, 0],
          }}
          transition={{
            duration: 3,
            delay: 1.12,
            repeat: Infinity,
            ease: "easeInOut",
          }}
        >
          EST. 2001
        </motion.text>

      </svg>
    </div>
  );
}