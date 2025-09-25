// App.js
import React from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Ionicons } from "@expo/vector-icons";

import CheckoutScreen from "./components/checkoutscreen";
import TrailShareScreen from "./components/trailShareScreen";

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
          tabBarIcon: ({ color, size }) => {
            let iconName;

            if (route.name === "Shop") {
              iconName = "cart";
            } else if (route.name === "Trails") {
              iconName = "map";
            }

            return <Ionicons name={iconName} size={size} color={color} />;
          },
          tabBarActiveTintColor: "#1e40af",
          tabBarInactiveTintColor: "gray",
          headerShown: false,
        })}
      >
        <Tab.Screen name="Shop" component={CheckoutScreen} />
        <Tab.Screen name="Trails" component={TrailShareScreen} />
      </Tab.Navigator>
    </NavigationContainer>
  );
}
