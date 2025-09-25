// styles/styles.js
import { StyleSheet } from 'react-native';

export const styles = StyleSheet.create({
  // ===== Common / Global Layout =====
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f0f9ff',
  },
  scrollView: {
    flex: 1,
  },
  scrollContent: {
    paddingBottom: 10,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 20,
    color: '#1e40af',
    marginTop: 10,
  },

  // ===== Cart / Checkout =====
  orderSummary: {
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 10,
    marginBottom: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  itemRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 12,
    paddingBottom: 12,
    borderBottomWidth: 1,
    borderBottomColor: '#f3f4f6',
  },
  summaryText: {
    fontSize: 15,
    flex: 1,
  },
  removedItem: {
    color: '#9ca3af',
    textDecorationLine: 'line-through',
  },
  quantityContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#f3f4f6',
    borderRadius: 5,
    padding: 5,
  },
  quantityButton: {
    fontSize: 16,
    fontWeight: 'bold',
    paddingHorizontal: 8,
    color: '#3b82f6',
  },
  quantityText: {
    fontSize: 14,
    fontWeight: 'bold',
    marginHorizontal: 8,
  },
  zeroQuantity: {
    color: '#ef4444',
  },
  totalText: {
    fontSize: 20,
    fontWeight: 'bold',
    marginTop: 15,
    color: '#1e40af',
    borderTopWidth: 1,
    borderTopColor: '#e5e7eb',
    paddingTop: 10,
  },
  emptyCartText: {
    textAlign: 'center',
    color: '#6b7280',
    fontStyle: 'italic',
    marginTop: 10,
  },
  checkoutButton: {
    backgroundColor: '#3b82f6',
    padding: 16,
    borderRadius: 10,
    alignItems: 'center',
    marginTop: 10,
  },
  disabledButton: {
    backgroundColor: '#9ca3af',
  },
  buttonText: {
    color: 'white',
    fontSize: 18,
    fontWeight: 'bold',
  },

  // ===== Inputs & Map =====
  input: {
    backgroundColor: 'white',
    padding: 15,
    borderRadius: 10,
    marginBottom: 20,
    borderWidth: 1,
    borderColor: '#d1d5db',
  },
  mapContainer: {
    height: 300,
    borderRadius: 10,
    overflow: 'hidden',
    marginBottom: 20,
  },
  map: {
    width: '100%',
    height: '100%',
  },

  // ===== Trail Share Screen =====
  trailResult: {
    marginTop: 20,
    alignItems: 'center',
  },
  trailText: {
    fontSize: 16,
    fontWeight: '500',
    color: '#374151',
  },
});
