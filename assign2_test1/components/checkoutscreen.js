// Main checkout screen component
import React, { useState } from 'react';
import { View, Text } from 'react-native';
import { styles } from '../styles/styles';
import ProductList from './productslist';
import CheckoutButton from './checkoutbutton';
import { productDetails } from '../data/products';


const CheckoutScreen = () => {
  const [quantities, setQuantities] = useState(
    Object.keys(productDetails).reduce((acc, product) => {
      acc[product] = 1;
      return acc;
    }, {})
  );

  const total = Object.keys(quantities).reduce((sum, product) => {
    return sum + (quantities[product] * productDetails[product].price);
  }, 0);

  const updateQuantity = (product, change) => {
    setQuantities(prev => ({
      ...prev,
      [product]: Math.max(0, prev[product] + change)
    }));
  };

  const handleCheckout = () => {
    const purchasedItems = Object.keys(quantities)
      .filter(product => quantities[product] > 0)
      .map(product => `${quantities[product]} x ${productDetails[product].name}`);
    
    alert(`Order placed!\nItems: ${purchasedItems.join(', ')}\nTotal: $${total.toFixed(2)}`);
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>🏕️ Adventure Gear Marketplace</Text>
      
      <ProductList 
        quantities={quantities}
        onUpdateQuantity={updateQuantity}
        products={productDetails}
        total={total}
      />

      <CheckoutButton total={total} onCheckout={handleCheckout} />
    </View>
  );
};

export default CheckoutScreen;