import { Badge } from '@/components/ui/badge';

export const StockBadge = ({ quantidade }: { quantidade: number }) => {
  const QUATITY_RULES = {
    NO_STOCK: 0,
    LOW_STOCK: 100,
  };

  if (quantidade === QUATITY_RULES.NO_STOCK)
    return <Badge variant="destructive">Sem estoque</Badge>;
  if (quantidade <= QUATITY_RULES.LOW_STOCK)
    return (
      <Badge className="bg-yellow-50 text-yellow-700 dark:bg-yellow-950 dark:text-yellow-300">
        Baixo ({quantidade})
      </Badge>
    );
  return (
    <Badge className="bg-green-50 text-green-700 dark:bg-green-950 dark:text-green-300">
      Em estoque ({quantidade})
    </Badge>
  );
};
