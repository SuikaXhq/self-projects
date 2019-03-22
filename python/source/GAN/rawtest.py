from test import show_result
import numpy as np
from tensorflow.examples.tutorials.mnist import input_data

mnist = input_data.read_data_sets('MNIST_data', one_hot=True)
for i in range(10):
    print(i)
    x_value, _ = mnist.train.next_batch(64)
    x_value = 2 * x_value.astype(np.float32) - 1
    show_result(x_value, 'raw/{0}.jpg'.format(i))