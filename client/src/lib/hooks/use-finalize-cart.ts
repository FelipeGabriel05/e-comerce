import { useState } from 'react';
import { toast } from 'sonner';

import { api } from '@/lib/services/constants';

export const useFinalizeCart = () => {
  const [isSubmitting, setIsSubmitting] = useState(false);

  const finalizeCart = async () => {
    if (!confirm('Deseja finalizar a compra?')) return;
    setIsSubmitting(true);
    try {
      await api.post('/checkout');
      toast.success('Compra finalizada com sucesso!');
    } catch (_error) {
      toast.error('Erro ao finalizar compra. Tente novamente.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return {
    finalizeCart,
    isSubmitting,
  };
};
