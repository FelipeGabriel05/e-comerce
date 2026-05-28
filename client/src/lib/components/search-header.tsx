import { Search } from 'lucide-react';

import { Button as ButtonUi } from '@/components/ui/button';
import { ButtonGroup } from '@/components/ui/button-group';
import { Field } from '@/components/ui/field';
import { Input } from '@/components/ui/input';

const SearchHeader = () => {
  return (
    <Field>
      <ButtonGroup>
        <Input
          id="input-button-group"
          className="bg-purple-700 h-10 font-bold w-full"
          placeholder="Pesquisar"
        />
        <ButtonUi className={'bg-purple-700 h-10'}>
          <Search />
        </ButtonUi>
      </ButtonGroup>
    </Field>
  );
};

export default SearchHeader;
