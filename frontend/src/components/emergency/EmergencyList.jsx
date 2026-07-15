import EmergencyCard from "./EmergencyCard";

const contacts = [

    {
        name: "John Doe",
        relation: "Brother",
        phone: "+91 9876543210"
    },

    {
        name: "Alice",
        relation: "Friend",
        phone: "+91 9123456780"
    },

    {
        name: "David",
        relation: "Father",
        phone: "+91 9988776655"
    }

];

export default function EmergencyList() {

    return (

        <div className="grid lg:grid-cols-2 gap-6">

            {

                contacts.map((contact, index) => (

                    <EmergencyCard
                        key={index}
                        {...contact}
                    />

                ))

            }

        </div>

    );

}