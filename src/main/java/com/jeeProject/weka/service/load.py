from tensorflow.python.keras.models import *
from tensorflow.python.keras.layers import *
from keras.preprocessing import image
import numpy as np
import sys

test_image = image.load_img(sys.argv[0],target_size=(64,64))
test_image = image.img_to_array(test_image)
test_image = np.expand_dims(test_image, axis = 0)

model = load_model("Model")
print(model.predict(test_image))