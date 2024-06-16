static int reduceThreads(int threads) {
    if (threads < 2) return 1 else return (int) (threads * 0.7)
}
return this