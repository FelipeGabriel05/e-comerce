import { useState } from 'react';
import { toast } from 'sonner';

import { checkoutService } from '@/lib/services/checkout.services';

export const useFinalizeCart = () => {
  const [isSubmitting, setIsSubmitting] = useState(false);

  const finalizeCart = async () => {
    setIsSubmitting(true);
    try {
      await checkoutService();
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
