from java import jclass
import torch
import torch.nn as nn
import torch.nn.functional as F


class Net(nn.Module):

    def __init__(self):
        super(Net, self).__init__()
        # 1 input image channel, 6 output channels, 3x3 square convolution
        # kernel
        self.conv1 = nn.Conv2d(1, 6, 3)
        self.conv2 = nn.Conv2d(6, 16, 3)
        # an affine operation: y = Wx + b
        self.fc1 = nn.Linear(16 * 6 * 6, 120)  # 6*6 from image dimension
        self.fc2 = nn.Linear(120, 84)
        self.fc3 = nn.Linear(84, 10)

    def forward(self, x):
        # Max pooling over a (2, 2) window
        x = F.max_pool2d(F.relu(self.conv1(x)), (2, 2))
        # If the size is a square you can only specify a single number
        x = F.max_pool2d(F.relu(self.conv2(x)), 2)
        x = x.view(-1, self.num_flat_features(x))
        x = F.relu(self.fc1(x))
        x = F.relu(self.fc2(x))
        x = self.fc3(x)
        return x

    def num_flat_features(self, x):
        size = x.size()[1:]  # all dimensions except the batch dimension
        num_features = 1
        for s in size:
            num_features *= s
        return num_features



def greet(name):
    print("--- hello,%s ---" % name)


def add(a,b):
    example = torch.rand(1, 3, 224, 224)
    net = Net()
    print(net)
    return a + b

def sub(count,a=0,b=0,c=0):
    return count - a - b -c

def get_list(a,b,c,d):
    return [a,b,c,d]

def print_list(data):
    print(type(data))
    # 遍历Java的ArrayList对象
    for i in range(data.size()):
        print(data.get(i))

# python调用Java类
def get_java_bean(a):
    JavaBean = jclass("test.JavaBean")#用自己的包名
    jb = JavaBean(a)
    samples=jb.getSamples()
    return samples.get(0).getRate()
