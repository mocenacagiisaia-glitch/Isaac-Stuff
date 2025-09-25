// components/TrailShareScreen.js
import React, { useState } from "react";
import { View, Text, TextInput, Button, Image } from "react-native";
import { styles } from "../styles/styles";  // ✅ now all styles in one place

const SAMPLE_GIPHY =
  "https://media.giphy.com/media/3o6Zt481isNVuQI1l6/giphy.gif";

export default function TrailShareScreen() {
  const [trailName, setTrailName] = useState("");
  const [sharedTrail, setSharedTrail] = useState(null);

  const handleShare = () => {
    if (trailName.trim() !== "") {
      setSharedTrail({
        name: trailName,
        gif: SAMPLE_GIPHY,
      });
      setTrailName("");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Share Your Favorite Trail</Text>

      <TextInput
        style={styles.input}
        placeholder="Enter trail name"
        value={trailName}
        onChangeText={setTrailName}
      />

      <Button title="Share Trail" onPress={handleShare} />

      {sharedTrail && (
        <View style={styles.trailResult}>
          <Text style={styles.trailText}>🌲 You shared: {sharedTrail.name}</Text>
          <Image
            source={{ uri: sharedTrail.gif }}
            style={{ width: 200, height: 150, marginTop: 10 }}
          />
        </View>
      )}
    </View>
  );
}
