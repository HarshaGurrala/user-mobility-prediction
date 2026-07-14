export default function Input({

    type="text",

    placeholder,

    value,

    onChange

}){

    return(

        <input

            type={type}

            placeholder={placeholder}

            value={value}

            onChange={onChange}

            className="

            w-full

            rounded-2xl

            bg-white/5

            border

            border-white/10

            px-4

            py-3

            text-white

            outline-none

            focus:border-blue-500

            transition

            "

        />

    )

}