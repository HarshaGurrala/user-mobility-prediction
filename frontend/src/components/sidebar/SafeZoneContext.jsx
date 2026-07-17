import { createContext, useContext, useState } from "react";

const SafeZoneContext = createContext();

export function SafeZoneProvider({ children }) {

  const [position, setPosition] = useState(null);

  return (

    <SafeZoneContext.Provider
      value={{
        position,
        setPosition,
      }}
    >

      {children}

    </SafeZoneContext.Provider>

  );

}

export function useSafeZone() {

  return useContext(
    SafeZoneContext
  );

}