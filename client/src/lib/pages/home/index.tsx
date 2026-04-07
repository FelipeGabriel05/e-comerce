import { Button } from '@/components/ui/button';

import { CTASection } from './components/cta-section';

const Home = () => {
  return (
    <div className="flex min-h-[60vh] flex-col items-center justify-center gap-8 text-center">
      <CTASection />
      <Button> This is a button </Button>
    </div>
  );
};

export default Home;
