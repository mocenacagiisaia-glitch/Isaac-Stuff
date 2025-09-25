// Individual product row component
import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import { styles } from '../styles/styles';

const ProductItem = ({ product, quantity, onQuantityChange }) => {
  const { emoji, name, price } = product;

  return (
    <View style={styles.itemRow}>
      <Text style={[
        styles.summaryText,
        quantity === 0 && styles.removedItem
      ]}>
        {emoji} {name} - ${price}
        {quantity === 0 && ' (Removed)'}
      </Text>
      <View style={styles.quantityContainer}>
        <TouchableOpacity onPress={() => onQuantityChange(-1)}>
          <Text style={styles.quantityButton}>-</Text>
        </TouchableOpacity>
        <Text style={[
          styles.quantityText,
          quantity === 0 && styles.zeroQuantity
        ]}>{quantity}</Text>
        <TouchableOpacity onPress={() => onQuantityChange(1)}>
          <Text style={styles.quantityButton}>+</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

export default ProductItem;