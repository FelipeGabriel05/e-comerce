const SearchHeader = () => {
  return (
    <input
      type="search"
      placeholder="Buscar produtos..."
      className="w-full rounded-md border border-white/10 bg-white/5 px-4 py-2 text-sm text-white placeholder-gray-400 outline-none focus:border-purple-500 focus:ring-1 focus:ring-purple-500 transition"
    />
  );
};

export default SearchHeader;
