// List of all products component
import React from 'react';
import { View, Text, ScrollView } from 'react-native';
import { styles } from '../styles/styles';
import ProductItem from './productitem';

const ProductList = ({ quantities, onUpdateQuantity, products, total }) => {
  return (
    <ScrollView style={styles.scrollView} contentContainerStyle={styles.scrollContent}>
      <View style={styles.orderSummary}>
        {Object.keys(products).map(productKey => (
          <ProductItem
            key={productKey}
            product={products[productKey]}
            quantity={quantities[productKey]}
            onQuantityChange={(change) => onUpdateQuantity(productKey, change)}
          />
        ))}

        <Text style={styles.totalText}>Total: ${total.toFixed(2)}</Text>
        
        {total === 0 && (
          <Text style={styles.emptyCartText}>Your cart is empty! Add some items.</Text>
        )}
      </View>
    </ScrollView>
  );
};

export default ProductList;