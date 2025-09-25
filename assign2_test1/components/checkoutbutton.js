// Checkout button component
import React from 'react';
import { TouchableOpacity, Text } from 'react-native';
import { styles } from '../styles/styles';

const CheckoutButton = ({ total, onCheckout }) => {
  return (
    <TouchableOpacity 
      style={[
        styles.checkoutButton,
        total === 0 && styles.disabledButton
      ]} 
      onPress={onCheckout}
      disabled={total === 0}
    >
      <Text style={styles.buttonText}>
        {total === 0 ? 'Cart is Empty' : `Checkout - $${total.toFixed(2)}`}
      </Text>
    </TouchableOpacity>
  );
};

export default CheckoutButton;