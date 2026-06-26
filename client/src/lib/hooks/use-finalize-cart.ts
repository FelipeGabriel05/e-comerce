import { useState } from 'react';

import { api } from '@/lib/services/constants';

export const useFinalizeCart = () => {
  const [isSubmitting, setIsSubmitting] = useState(false);

  const finalizeCart = async () => {
    if (!confirm('Deseja finalizar a compra?')) return;
    setIsSubmitting(true);
    try {
      await api.post('/sales');
    } catch (error) {
      console.error('Erro ao finalizar compra:', error);
    } finally {
      setIsSubmitting(false);
    }
  };

  return {
    finalizeCart,
    isSubmitting,
  };
};
